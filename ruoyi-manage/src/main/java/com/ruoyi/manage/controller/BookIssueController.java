package com.ruoyi.manage.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.manage.domain.Book;
import com.ruoyi.manage.domain.vo.BookIssueVo;
import com.ruoyi.manage.service.IBookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.ruoyi.manage.domain.BookIssue;
import com.ruoyi.manage.service.IBookIssueService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户借阅Controller
 * 
 * @author tre2e
 * @date 2025-03-07
 */
@RestController
@RequestMapping("/manage/issue")
public class BookIssueController extends BaseController
{
    @Autowired
    private IBookIssueService bookIssueService;

    @Autowired
    private IBookService bookService; // 新增 BookService 依赖

    /**
     * 查询用户借阅列表
     */
    @PreAuthorize("@ss.hasPermi('manage:issue:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookIssue bookIssue)
    {
        startPage();
        List<BookIssueVo> list = bookIssueService.selectBookIssueList(bookIssue);   // 返回Vo
        return getDataTable(list);
    }

    /**
     * 查询当前用户的借阅列表（用户自助用）
     */
    @PreAuthorize("isAuthenticated()") // 仅需登录
    @GetMapping("/myList")
    public TableDataInfo myList(BookIssue bookIssue)
    {
        startPage();
        // 设置当前登录用户的 ID
        Long currentUserId = SecurityUtils.getUserId(); // 获取当前用户 ID
        bookIssue.setUserId(currentUserId); // 过滤只显示当前用户的记录
        List<BookIssueVo> list = bookIssueService.selectBookIssueList(bookIssue);
        return getDataTable(list);
    }

    /**
     * 导出用户借阅列表
     */
    @PreAuthorize("@ss.hasPermi('manage:issue:export')")
    @Log(title = "用户借阅", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookIssue bookIssue)
    {
        List<BookIssueVo> list = bookIssueService.selectBookIssueList(bookIssue);   // 返回Vo
        ExcelUtil<BookIssueVo> util = new ExcelUtil<BookIssueVo>(BookIssueVo.class);
        util.exportExcel(response, list, "用户借阅数据");
    }

    /**
     * 获取用户借阅详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:issue:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bookIssueService.selectBookIssueById(id));
    }

    /**
     * 新增用户借阅
     */
    @PreAuthorize("@ss.hasPermi('manage:issue:add')")
    @Log(title = "用户借阅", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookIssue bookIssue)
    {
        return toAjax(bookIssueService.insertBookIssue(bookIssue));
    }

    /**
     * 新增用户借阅并更新库存
     */
    @PreAuthorize("isAuthenticated()") // 所有登录用户可用
    @Log(title = "用户借阅", businessType = BusinessType.INSERT)
    @PostMapping("/addWithQuantity")
    @Transactional
    public AjaxResult addWithQuantity(@RequestBody BookIssue bookIssue) {
        Book book = bookService.selectBookById(bookIssue.getBookId());
        if (book == null || book.getQuantity() <= 0) {
            return AjaxResult.error("库存不足");
        }

        // 检查用户借阅总数
        List<BookIssueVo> userIssues = bookIssueService.selectBookIssueList(
                new BookIssue() {{ setUserId(bookIssue.getUserId()); setStatus(0); }}
        );
        if (userIssues.size() >= 10) {
            return AjaxResult.error("您已借阅10本书，无法再借更多");
        }

        // 检查重复借阅
        boolean hasBorrowed = userIssues.stream().anyMatch(item -> item.getBookId() != null && item.getBookId().equals(bookIssue.getBookId()));
        if (hasBorrowed) {
            return AjaxResult.error("您已借阅此书，无法重复借阅");
        }

        book.setQuantity(book.getQuantity() - 1);               // 借出一本书
        bookService.updateBook(book);
        int result = bookIssueService.insertBookIssue(bookIssue);
        return toAjax(result);
    }

    /**
     * 修改用户借阅
     */
    @PreAuthorize("@ss.hasPermi('manage:issue:edit')")
    @Log(title = "用户借阅", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookIssue bookIssue)
    {
        return toAjax(bookIssueService.updateBookIssue(bookIssue));
    }

    /**
     * 归还用户借阅并更新库存
     */
    @PreAuthorize("isAuthenticated()") // 所有登录用户可用
    @Log(title = "用户借阅", businessType = BusinessType.UPDATE)
    @PutMapping("/return/{issueId}")
    @Transactional
    public AjaxResult returnIssue(@PathVariable("issueId") Long issueId) {
        BookIssue issue = bookIssueService.selectBookIssueById(issueId);
        if (issue == null || issue.getStatus() == 1) {
            return AjaxResult.error("借阅记录无效或已归还");
        }
        issue.setStatus(1);
        issue.setReturnDate(new Date());        // 设置归还日期为当前时间
        bookIssueService.updateBookIssue(issue);
        Book book = bookService.selectBookById(issue.getBookId());
        book.setQuantity(book.getQuantity() + 1);
        bookService.updateBook(book);
        return AjaxResult.success();
    }

    /**
     * 删除用户借阅
     */
    @PreAuthorize("@ss.hasPermi('manage:issue:remove')")
    @Log(title = "用户借阅", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bookIssueService.deleteBookIssueByIds(ids));
    }
}
