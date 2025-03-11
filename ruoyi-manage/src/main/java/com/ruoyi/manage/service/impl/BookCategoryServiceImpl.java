package com.ruoyi.manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.manage.mapper.BookCategoryMapper;
import com.ruoyi.manage.domain.BookCategory;
import com.ruoyi.manage.service.IBookCategoryService;

/**
 * 类别管理Service业务层处理
 * 
 * @author tre2e
 * @date 2025-03-05
 */
@Service
public class BookCategoryServiceImpl implements IBookCategoryService 
{
    @Autowired
    private BookCategoryMapper bookCategoryMapper;

    /**
     * 查询类别管理
     * 
     * @param id 类别管理主键
     * @return 类别管理
     */
    @Override
    public BookCategory selectBookCategoryById(Long id)
    {
        return bookCategoryMapper.selectBookCategoryById(id);
    }

    /**
     * 查询类别管理列表
     * 
     * @param bookCategory 类别管理
     * @return 类别管理
     */
    @Override
    public List<BookCategory> selectBookCategoryList(BookCategory bookCategory)
    {
        return bookCategoryMapper.selectBookCategoryList(bookCategory);
    }

    /**
     * 新增类别管理
     * 
     * @param bookCategory 类别管理
     * @return 结果
     */
    @Override
    public int insertBookCategory(BookCategory bookCategory)
    {
        bookCategory.setCreateTime(DateUtils.getNowDate());
        return bookCategoryMapper.insertBookCategory(bookCategory);
    }

    /**
     * 修改类别管理
     * 
     * @param bookCategory 类别管理
     * @return 结果
     */
    @Override
    public int updateBookCategory(BookCategory bookCategory)
    {
        bookCategory.setUpdateTime(DateUtils.getNowDate());
        return bookCategoryMapper.updateBookCategory(bookCategory);
    }

    /**
     * 批量删除类别管理
     * 
     * @param ids 需要删除的类别管理主键
     * @return 结果
     */
    @Override
    public int deleteBookCategoryByIds(Long[] ids)
    {
        return bookCategoryMapper.deleteBookCategoryByIds(ids);
    }

    /**
     * 删除类别管理信息
     * 
     * @param id 类别管理主键
     * @return 结果
     */
    @Override
    public int deleteBookCategoryById(Long id)
    {
        return bookCategoryMapper.deleteBookCategoryById(id);
    }
}
