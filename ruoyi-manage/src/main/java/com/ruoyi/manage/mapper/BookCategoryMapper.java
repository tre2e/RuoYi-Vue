package com.ruoyi.manage.mapper;

import java.util.List;
import com.ruoyi.manage.domain.BookCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类别管理Mapper接口
 * 
 * @author tre2e
 * @date 2025-03-05
 */
@Mapper
public interface BookCategoryMapper 
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
     * 删除类别管理
     * 
     * @param id 类别管理主键
     * @return 结果
     */
    public int deleteBookCategoryById(Long id);

    /**
     * 批量删除类别管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookCategoryByIds(Long[] ids);
}
