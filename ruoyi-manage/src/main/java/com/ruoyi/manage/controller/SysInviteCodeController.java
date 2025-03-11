package com.ruoyi.manage.controller;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.manage.domain.SysInviteCode;
import com.ruoyi.manage.service.ISysInviteCodeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 邀请码Controller
 * 
 * @author tre2e
 * @date 2025-03-10
 */
@RestController
@RequestMapping("/manage/invitecode")
public class SysInviteCodeController extends BaseController
{
    @Autowired
    private ISysInviteCodeService sysInviteCodeService;


    private static final String PREFIX = "INV-";
    private static final int RANDOM_LENGTH = 6;
    private static final String CHARACTERS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 查询邀请码列表
     */
    @PreAuthorize("@ss.hasPermi('manage:invitecode:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysInviteCode sysInviteCode)
    {
        startPage();
        List<SysInviteCode> list = sysInviteCodeService.selectSysInviteCodeList(sysInviteCode);
        return getDataTable(list);
    }

    /**
     * 导出邀请码列表
     */
    @PreAuthorize("@ss.hasPermi('manage:invitecode:export')")
    @Log(title = "邀请码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysInviteCode sysInviteCode)
    {
        List<SysInviteCode> list = sysInviteCodeService.selectSysInviteCodeList(sysInviteCode);
        ExcelUtil<SysInviteCode> util = new ExcelUtil<SysInviteCode>(SysInviteCode.class);
        util.exportExcel(response, list, "邀请码数据");
    }

    /**
     * 获取邀请码详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:invitecode:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysInviteCodeService.selectSysInviteCodeById(id));
    }

    /**
     * 新增邀请码
     */
    @PreAuthorize("@ss.hasPermi('manage:invitecode:add')")
    @Log(title = "邀请码", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add() {
        SysInviteCode sysInviteCode = new SysInviteCode();
        String inviteCode = generateInviteCode();
        sysInviteCode.setInviteCode(inviteCode);
        sysInviteCode.setIsUsed(0);
        sysInviteCode.setCreateBy(getUsername());
        sysInviteCode.setExpireTime(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)); // 30天后过期
        int result = sysInviteCodeService.insertSysInviteCode(sysInviteCode);
        return result > 0 ? AjaxResult.success("生成邀请码成功", inviteCode) : error("生成邀请码失败");
    }

    /**
     * 修改邀请码
     */
    @PreAuthorize("@ss.hasPermi('manage:invitecode:edit')")
    @Log(title = "邀请码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysInviteCode sysInviteCode)
    {
        return toAjax(sysInviteCodeService.updateSysInviteCode(sysInviteCode));
    }

    /**
     * 删除邀请码
     */
    @PreAuthorize("@ss.hasPermi('manage:invitecode:remove')")
    @Log(title = "邀请码", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysInviteCodeService.deleteSysInviteCodeByIds(ids));
    }

    /**
     * 生成唯一邀请码
     *
     * */
    private String generateInviteCode() {
        StringBuilder code;
        do {
            code = new StringBuilder(PREFIX);
            for (int i = 0; i < RANDOM_LENGTH; i++) {
                code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
            }
        } while (sysInviteCodeService.selectSysInviteCodeByCode(code.toString()) != null);
        return code.toString();
    }

}
