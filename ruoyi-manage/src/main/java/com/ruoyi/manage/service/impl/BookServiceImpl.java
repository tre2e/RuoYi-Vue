package com.ruoyi.manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.manage.mapper.BookMapper;
import com.ruoyi.manage.domain.Book;
import com.ruoyi.manage.service.IBookService;

/**
 * 书籍Service业务层处理
 * 
 * @author tre2e
 * @date 2025-03-05
 */
@Service
public class BookServiceImpl implements IBookService 
{
    @Autowired
    private BookMapper bookMapper;

    /**
     * 查询书籍
     * 
     * @param id 书籍主键
     * @return 书籍
     */
    @Override
    public Book selectBookById(Long id)
    {
        return bookMapper.selectBookById(id);
    }

    /**
     * 查询书籍列表
     * 
     * @param book 书籍
     * @return 书籍
     */
    @Override
    public List<Book> selectBookList(Book book)
    {
        // 调用Mapper查询数据
        List<Book> booklist = bookMapper.selectBookList(book);

        // 转换 status 为描述文字
        for (Book book1 : booklist) {
            if (book1.getStatus() != null) {
                switch (book1.getStatus()) {
                    case 0:
                        book1.setStatusName("不可借");
                        break;
                    case 1:
                        book1.setStatusName("可借");
                        break;
                    case 2:
                        book1.setStatusName("未到馆");
                        break;
                    default:
                        book1.setStatusName("请询问管理员");
                }
            }
        }
        return booklist;
    }

    /**
     * 新增书籍
     * 
     * @param book 书籍
     * @return 结果
     */
    @Override
    public int insertBook(Book book)
    {
        book.setCreateTime(DateUtils.getNowDate());
        return bookMapper.insertBook(book);
    }

    /**
     * 修改书籍
     * 
     * @param book 书籍
     * @return 结果
     */
    @Override
    public int updateBook(Book book)
    {
        book.setUpdateTime(DateUtils.getNowDate());
        return bookMapper.updateBook(book);
    }

    /**
     * 批量删除书籍
     * 
     * @param ids 需要删除的书籍主键
     * @return 结果
     */
    @Override
    public int deleteBookByIds(Long[] ids)
    {
        return bookMapper.deleteBookByIds(ids);
    }

    /**
     * 删除书籍信息
     * 
     * @param id 书籍主键
     * @return 结果
     */
    @Override
    public int deleteBookById(Long id)
    {
        return bookMapper.deleteBookById(id);
    }
}
