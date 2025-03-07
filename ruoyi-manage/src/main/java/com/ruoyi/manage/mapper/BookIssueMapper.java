package com.ruoyi.manage.mapper;

import java.util.List;
import com.ruoyi.manage.domain.BookIssue;
import com.ruoyi.manage.domain.vo.BookIssueVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户借阅Mapper接口
 * 
 * @author tre2e
 * @date 2025-03-07
 */
@Mapper
public interface BookIssueMapper 
{
    /**
     * 查询用户借阅
     * 
     * @param id 用户借阅主键
     * @return 用户借阅
     */
    public BookIssue selectBookIssueById(Long id);

    /**
     * 查询用户借阅列表
     * 
     * @param bookIssue 用户借阅
     * @return 用户借阅集合
     */
    public List<BookIssueVo> selectBookIssueList(BookIssue bookIssue);  // 修改为返回Vo

    /**
     * 新增用户借阅
     * 
     * @param bookIssue 用户借阅
     * @return 结果
     */
    public int insertBookIssue(BookIssue bookIssue);

    /**
     * 修改用户借阅
     * 
     * @param bookIssue 用户借阅
     * @return 结果
     */
    public int updateBookIssue(BookIssue bookIssue);

    /**
     * 删除用户借阅
     * 
     * @param id 用户借阅主键
     * @return 结果
     */
    public int deleteBookIssueById(Long id);

    /**
     * 批量删除用户借阅
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookIssueByIds(Long[] ids);
}
