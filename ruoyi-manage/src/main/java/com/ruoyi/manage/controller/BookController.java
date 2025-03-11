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
import com.ruoyi.manage.domain.Book;
import com.ruoyi.manage.service.IBookService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 书籍Controller
 * 
 * @author tre2e
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/manage/book")
public class BookController extends BaseController
{
    @Autowired
    private IBookService bookService;

    /**
     * 查询书籍列表
     */
    @PreAuthorize("@ss.hasPermi('manage:book:list')")
    @GetMapping("/list")
    public TableDataInfo list(Book book)
    {
        startPage();
        List<Book> list = bookService.selectBookList(book);
        return getDataTable(list);
    }

    // 新增检索接口（无需权限或仅需登录）
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public TableDataInfo search(Book book) {
        startPage();
        List<Book> list = bookService.selectBookList(book);
        return getDataTable(list);
    }

    /**
     * 导出书籍列表
     */
    @PreAuthorize("@ss.hasPermi('manage:book:export')")
    @Log(title = "书籍", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Book book)
    {
        List<Book> list = bookService.selectBookList(book);
        ExcelUtil<Book> util = new ExcelUtil<Book>(Book.class);
        util.exportExcel(response, list, "书籍数据");
    }

    /**
     * 获取书籍详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:book:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bookService.selectBookById(id));
    }

    /**
     * 新增书籍
     */
    @PreAuthorize("@ss.hasPermi('manage:book:add')")
    @Log(title = "书籍", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Book book)
    {
        return toAjax(bookService.insertBook(book));
    }

    /**
     * 修改书籍
     */
    @PreAuthorize("@ss.hasPermi('manage:book:edit')")
    @Log(title = "书籍", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Book book)
    {
        return toAjax(bookService.updateBook(book));
    }

    /**
     * 删除书籍
     */
    @PreAuthorize("@ss.hasPermi('manage:book:remove')")
    @Log(title = "书籍", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bookService.deleteBookByIds(ids));
    }
}
