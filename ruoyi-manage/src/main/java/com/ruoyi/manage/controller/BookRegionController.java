package com.ruoyi.manage.controller;

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
import com.ruoyi.manage.domain.BookRegion;
import com.ruoyi.manage.service.IBookRegionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 区域管理Controller
 * 
 * @author tre2e
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/manage/region")
public class BookRegionController extends BaseController
{
    @Autowired
    private IBookRegionService bookRegionService;

    /**
     * 查询区域管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:region:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookRegion bookRegion)
    {
        startPage();
        List<BookRegion> list = bookRegionService.selectBookRegionList(bookRegion);
        return getDataTable(list);
    }

    /**
     * 用户检索书籍区域
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public TableDataInfo searchList(BookRegion bookRegion)
    {
        startPage();
        List<BookRegion> list = bookRegionService.selectBookRegionList(bookRegion);
        return getDataTable(list);
    }

    /**
     * 导出区域管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:region:export')")
    @Log(title = "区域管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookRegion bookRegion)
    {
        List<BookRegion> list = bookRegionService.selectBookRegionList(bookRegion);
        ExcelUtil<BookRegion> util = new ExcelUtil<BookRegion>(BookRegion.class);
        util.exportExcel(response, list, "区域管理数据");
    }

    /**
     * 获取区域管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:region:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bookRegionService.selectBookRegionById(id));
    }

    /**
     * 新增区域管理
     */
    @PreAuthorize("@ss.hasPermi('manage:region:add')")
    @Log(title = "区域管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookRegion bookRegion)
    {
        return toAjax(bookRegionService.insertBookRegion(bookRegion));
    }

    /**
     * 修改区域管理
     */
    @PreAuthorize("@ss.hasPermi('manage:region:edit')")
    @Log(title = "区域管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookRegion bookRegion)
    {
        return toAjax(bookRegionService.updateBookRegion(bookRegion));
    }

    /**
     * 删除区域管理
     */
    @PreAuthorize("@ss.hasPermi('manage:region:remove')")
    @Log(title = "区域管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bookRegionService.deleteBookRegionByIds(ids));
    }
}
