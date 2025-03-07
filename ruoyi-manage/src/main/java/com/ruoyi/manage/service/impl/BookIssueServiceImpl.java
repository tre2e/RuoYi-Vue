package com.ruoyi.manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.manage.domain.vo.BookIssueVo;
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
        return bookIssueMapper.selectBookIssueList(bookIssue);
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
        bookIssue.setUpdateTime(DateUtils.getNowDate());
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
}
