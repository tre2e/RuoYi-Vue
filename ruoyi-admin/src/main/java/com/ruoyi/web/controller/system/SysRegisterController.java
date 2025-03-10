package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysRegisterService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.manage.domain.SysInviteCode;
import com.ruoyi.manage.service.ISysInviteCodeService;

import java.util.Date;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysInviteCodeService inviteCodeService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }

        // 获取邀请码并校验
        String inviteCodeStr = user.getInviteCode();
        if (StringUtils.isEmpty(inviteCodeStr)) {
            return error("请输入邀请码！");
        }

        SysInviteCode inviteCode = inviteCodeService.selectSysInviteCodeByCode(inviteCodeStr);
        if (inviteCode == null) {
            return error("邀请码无效！");
        }
        if (inviteCode.getIsUsed() == 1) {
            return error("邀请码已被使用！");
        }
        if (inviteCode.getExpireTime() != null && inviteCode.getExpireTime().before(new Date())) {
            return error("邀请码已过期！");
        }

        // 执行注册
        String msg = registerService.register(user);
        if (StringUtils.isEmpty(msg)) {
            // 注册成功，从user对象中获取新用户ID
            Long userId = user.getUserId();
            if (userId == null) {
                logger.error("注册成功但无法获取用户ID");
                return error("注册失败：无法获取用户ID");
            }

            // 更新邀请码状态
            inviteCode.setIsUsed(1);
            inviteCode.setUsedBy(userId); // 当前用户ID
            inviteCode.setUsedTime(new Date());
            int result = inviteCodeService.updateSysInviteCode(inviteCode);
            logger.info("更新邀请码状态结果: {}", result); // 记录更新结果
            return success("注册成功");
        }
        return error(msg);
    }
}
