package com.ruoyi.manage.service;

import java.util.List;
import com.ruoyi.manage.domain.BookCategory;

/**
 * 类别管理Service接口
 * 
 * @author tre2e
 * @date 2025-03-05
 */
public interface IBookCategoryService 
{
    /**
     * 查询类别管理
     * 
     * @param id 类别管理主键
     * @return 类别管理
     */
    public BookCategory selectBookCategoryById(Long id);

    /**
     * 查询类别管理列表
     * 
     * @param bookCategory 类别管理
     * @return 类别管理集合
     */
    public List<BookCategory> selectBookCategoryList(BookCategory bookCategory);

    /**
     * 新增类别管理
     * 
     * @param bookCategory 类别管理
     * @return 结果
     */
    public int insertBookCategory(BookCategory bookCategory);

    /**
     * 修改类别管理
     * 
     * @param bookCategory 类别管理
     * @return 结果
     */
    public int updateBookCategory(BookCategory bookCategory);

    /**
     * 批量删除类别管理
     * 
     * @param ids 需要删除的类别管理主键集合
     * @return 结果
     */
    public int deleteBookCategoryByIds(Long[] ids);

    /**
     * 删除类别管理信息
     * 
     * @param id 类别管理主键
     * @return 结果
     */
    public int deleteBookCategoryById(Long id);
}
