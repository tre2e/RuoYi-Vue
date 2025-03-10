package com.ruoyi.manage.mapper;

import java.util.List;
import com.ruoyi.manage.domain.SysInviteCode;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邀请码Mapper接口
 * 
 * @author tre2e
 * @date 2025-03-10
 */
@Mapper
public interface SysInviteCodeMapper 
{
    /**
     * 查询邀请码
     * 
     * @param id 邀请码主键
     * @return 邀请码
     */
    public SysInviteCode selectSysInviteCodeById(Long id);

    /**
     * 查询邀请码列表
     * 
     * @param sysInviteCode 邀请码
     * @return 邀请码集合
     */
    public List<SysInviteCode> selectSysInviteCodeList(SysInviteCode sysInviteCode);

    /**
     * 新增邀请码
     * 
     * @param sysInviteCode 邀请码
     * @return 结果
     */
    public int insertSysInviteCode(SysInviteCode sysInviteCode);

    /**
     * 修改邀请码
     * 
     * @param sysInviteCode 邀请码
     * @return 结果
     */
    public int updateSysInviteCode(SysInviteCode sysInviteCode);

    /**
     * 删除邀请码
     * 
     * @param id 邀请码主键
     * @return 结果
     */
    public int deleteSysInviteCodeById(Long id);

    /**
     * 批量删除邀请码
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysInviteCodeByIds(Long[] ids);

    /**
     * 根据邀请码查询
     * @param inviteCode 邀请码
     * @return 邀请码对象
     */
    public SysInviteCode selectSysInviteCodeByCode(String inviteCode);
}
