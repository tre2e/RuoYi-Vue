package com.ruoyi.manage.mapper;

import java.util.List;
import com.ruoyi.manage.domain.BookRegion;
import org.apache.ibatis.annotations.Mapper;

import javax.management.MXBean;

/**
 * 区域管理Mapper接口
 * 
 * @author tre2e
 * @date 2025-03-05
 */
@Mapper
public interface BookRegionMapper 
{
    /**
     * 查询区域管理
     * 
     * @param id 区域管理主键
     * @return 区域管理
     */
    public BookRegion selectBookRegionById(Long id);

    /**
     * 查询区域管理列表
     * 
     * @param bookRegion 区域管理
     * @return 区域管理集合
     */
    public List<BookRegion> selectBookRegionList(BookRegion bookRegion);

    /**
     * 新增区域管理
     * 
     * @param bookRegion 区域管理
     * @return 结果
     */
    public int insertBookRegion(BookRegion bookRegion);

    /**
     * 修改区域管理
     * 
     * @param bookRegion 区域管理
     * @return 结果
     */
    public int updateBookRegion(BookRegion bookRegion);

    /**
     * 删除区域管理
     * 
     * @param id 区域管理主键
     * @return 结果
     */
    public int deleteBookRegionById(Long id);

    /**
     * 批量删除区域管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookRegionByIds(Long[] ids);
}
