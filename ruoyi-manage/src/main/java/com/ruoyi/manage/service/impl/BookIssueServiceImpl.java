package com.ruoyi.manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.manage.domain.Book;
import com.ruoyi.manage.domain.vo.BookIssueVo;
import com.ruoyi.manage.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.manage.mapper.BookIssueMapper;
import com.ruoyi.manage.domain.BookIssue;
import com.ruoyi.manage.service.IBookIssueService;

/**
 * 用户借阅Service业务层处理
 * 
 * @author tre2e
 * @date 2025-03-07
 */
@Service
public class BookIssueServiceImpl implements IBookIssueService 
{
    @Autowired
    private BookIssueMapper bookIssueMapper;

    @Autowired
    private IBookService bookService;       //  声明注入IBoookService

    /**
     * 查询用户借阅
     * 
     * @param id 用户借阅主键
     * @return 用户借阅
     */
    @Override
    public BookIssue selectBookIssueById(Long id)
    {
        return bookIssueMapper.selectBookIssueById(id);
    }

    /**
     * 查询用户借阅列表
     *
     * @param bookIssue 用户借阅
     * @return 用户借阅
     */
    @Override
    public List<BookIssueVo> selectBookIssueList(BookIssue bookIssue)   // 返回Vo
    {
        // 调用Mapper查询数据
        List<BookIssueVo> list = bookIssueMapper.selectBookIssueList(bookIssue);

        // 转换status为描述文字
        for (BookIssueVo bookIssueVo : list) {
            if (bookIssueVo.getStatus() != null) {
                switch (bookIssueVo.getStatus()) {
                    case 0:
                        bookIssueVo.setStatusName("未归还");
                        break;
                    case 1:
                        bookIssueVo.setStatusName("已归还");
                        break;
                    default:
                        bookIssueVo.setStatusName("请联系管理员");
                }
            }
        }
        return list;
    }



    /**
     * 新增用户借阅
     * 
     * @param bookIssue 用户借阅
     * @return 结果
     */
    @Override
    public int insertBookIssue(BookIssue bookIssue)
    {
        bookIssue.setCreateTime(DateUtils.getNowDate());
        return bookIssueMapper.insertBookIssue(bookIssue);
    }

    /**
     * 修改用户借阅
     * 
     * @param bookIssue 用户借阅
     * @return 结果
     */
    @Override
    public int updateBookIssue(BookIssue bookIssue)
    {
        /*bookIssue.setUpdateTime(DateUtils.getNowDate());*/
        // 如果归还日期不为空，状态设为“已归还”（1）
        BookIssue existingIssue = selectBookIssueById(bookIssue.getId());
        if (bookIssue.getReturnDate() != null) {
            bookIssue.setStatus(1);
            // 如果之前未归还，现在归还，增加库存
            if (existingIssue.getReturnDate() == null) {
                Book book = bookService.selectBookById(bookIssue.getBookId());
                book.setQuantity(book.getQuantity() + 1);
                bookService.updateBook(book);
            }
        } else {
            bookIssue.setStatus(0); // 如果归还日期为空，状态设为“已借出”（0）
        }
        return bookIssueMapper.updateBookIssue(bookIssue);
    }

    /**
     * 批量删除用户借阅
     * 
     * @param ids 需要删除的用户借阅主键
     * @return 结果
     */
    @Override
    public int deleteBookIssueByIds(Long[] ids)
    {
        return bookIssueMapper.deleteBookIssueByIds(ids);
    }

    /**
     * 删除用户借阅信息
     * 
     * @param id 用户借阅主键
     * @return 结果
     */
    @Override
    public int deleteBookIssueById(Long id)
    {
        return bookIssueMapper.deleteBookIssueById(id);
    }
    /**
     * 处理逾期用户信息
     *
     * */
    @Override
    public List<BookIssueVo> selectOverdueBookIssueList() {
        return bookIssueMapper.selectOverdueBookIssueList();
    }
}
