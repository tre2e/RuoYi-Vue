package com.ruoyi.manage.service;

import java.util.List;
import com.ruoyi.manage.domain.Book;

/**
 * 书籍Service接口
 * 
 * @author tre2e
 * @date 2025-03-05
 */
public interface IBookService 
{
    /**
     * 查询书籍
     * 
     * @param id 书籍主键
     * @return 书籍
     */
    public Book selectBookById(Long id);

    /**
     * 查询书籍列表
     * 
     * @param book 书籍
     * @return 书籍集合
     */
    public List<Book> selectBookList(Book book);

    /**
     * 新增书籍
     * 
     * @param book 书籍
     * @return 结果
     */
    public int insertBook(Book book);

    /**
     * 修改书籍
     * 
     * @param book 书籍
     * @return 结果
     */
    public int updateBook(Book book);

    /**
     * 批量删除书籍
     * 
     * @param ids 需要删除的书籍主键集合
     * @return 结果
     */
    public int deleteBookByIds(Long[] ids);

    /**
     * 删除书籍信息
     * 
     * @param id 书籍主键
     * @return 结果
     */
    public int deleteBookById(Long id);
}
