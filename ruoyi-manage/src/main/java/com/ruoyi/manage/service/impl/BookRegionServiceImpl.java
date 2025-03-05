package com.ruoyi.manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.manage.mapper.BookRegionMapper;
import com.ruoyi.manage.domain.BookRegion;
import com.ruoyi.manage.service.IBookRegionService;

/**
 * 区域管理Service业务层处理
 * 
 * @author tre2e
 * @date 2025-03-05
 */
@Service
public class BookRegionServiceImpl implements IBookRegionService 
{
    @Autowired
    private BookRegionMapper bookRegionMapper;

    /**
     * 查询区域管理
     * 
     * @param id 区域管理主键
     * @return 区域管理
     */
    @Override
    public BookRegion selectBookRegionById(Long id)
    {
        return bookRegionMapper.selectBookRegionById(id);
    }

    /**
     * 查询区域管理列表
     * 
     * @param bookRegion 区域管理
     * @return 区域管理
     */
    @Override
    public List<BookRegion> selectBookRegionList(BookRegion bookRegion)
    {
        return bookRegionMapper.selectBookRegionList(bookRegion);
    }

    /**
     * 新增区域管理
     * 
     * @param bookRegion 区域管理
     * @return 结果
     */
    @Override
    public int insertBookRegion(BookRegion bookRegion)
    {
        bookRegion.setCreateTime(DateUtils.getNowDate());
        return bookRegionMapper.insertBookRegion(bookRegion);
    }

    /**
     * 修改区域管理
     * 
     * @param bookRegion 区域管理
     * @return 结果
     */
    @Override
    public int updateBookRegion(BookRegion bookRegion)
    {
        bookRegion.setUpdateTime(DateUtils.getNowDate());
        return bookRegionMapper.updateBookRegion(bookRegion);
    }

    /**
     * 批量删除区域管理
     * 
     * @param ids 需要删除的区域管理主键
     * @return 结果
     */
    @Override
    public int deleteBookRegionByIds(Long[] ids)
    {
        return bookRegionMapper.deleteBookRegionByIds(ids);
    }

    /**
     * 删除区域管理信息
     * 
     * @param id 区域管理主键
     * @return 结果
     */
    @Override
    public int deleteBookRegionById(Long id)
    {
        return bookRegionMapper.deleteBookRegionById(id);
    }
}
