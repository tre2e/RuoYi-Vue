package com.ruoyi.manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.manage.mapper.SysInviteCodeMapper;
import com.ruoyi.manage.domain.SysInviteCode;
import com.ruoyi.manage.service.ISysInviteCodeService;

/**
 * 邀请码Service业务层处理
 * 
 * @author tre2e
 * @date 2025-03-10
 */
@Service
public class SysInviteCodeServiceImpl implements ISysInviteCodeService 
{
    @Autowired
    private SysInviteCodeMapper sysInviteCodeMapper;

    /**
     * 查询邀请码
     * 
     * @param id 邀请码主键
     * @return 邀请码
     */
    @Override
    public SysInviteCode selectSysInviteCodeById(Long id)
    {
        return sysInviteCodeMapper.selectSysInviteCodeById(id);
    }

    /**
     * 查询邀请码列表
     * 
     * @param sysInviteCode 邀请码
     * @return 邀请码
     */
    @Override
    public List<SysInviteCode> selectSysInviteCodeList(SysInviteCode sysInviteCode)
    {
        return sysInviteCodeMapper.selectSysInviteCodeList(sysInviteCode);
    }

    /**
     * 新增邀请码
     * 
     * @param sysInviteCode 邀请码
     * @return 结果
     */
    @Override
    public int insertSysInviteCode(SysInviteCode sysInviteCode)
    {
        sysInviteCode.setCreateTime(DateUtils.getNowDate());
        return sysInviteCodeMapper.insertSysInviteCode(sysInviteCode);
    }

    /**
     * 修改邀请码
     * 
     * @param sysInviteCode 邀请码
     * @return 结果
     */
    @Override
    public int updateSysInviteCode(SysInviteCode sysInviteCode)
    {
        sysInviteCode.setUpdateTime(DateUtils.getNowDate());
        return sysInviteCodeMapper.updateSysInviteCode(sysInviteCode);
    }

    /**
     * 批量删除邀请码
     * 
     * @param ids 需要删除的邀请码主键
     * @return 结果
     */
    @Override
    public int deleteSysInviteCodeByIds(Long[] ids)
    {
        return sysInviteCodeMapper.deleteSysInviteCodeByIds(ids);
    }

    /**
     * 删除邀请码信息
     * 
     * @param id 邀请码主键
     * @return 结果
     */
    @Override
    public int deleteSysInviteCodeById(Long id)
    {
        return sysInviteCodeMapper.deleteSysInviteCodeById(id);
    }

    /**
     * 实现通过邀请码查询
     *
     *
     * */
    @Override
    public SysInviteCode selectSysInviteCodeByCode(String inviteCode) {
        return sysInviteCodeMapper.selectSysInviteCodeByCode(inviteCode);
    }
}
