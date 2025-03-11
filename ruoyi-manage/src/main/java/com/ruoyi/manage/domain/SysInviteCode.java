package com.ruoyi.manage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 邀请码对象 sys_invite_code
 * 
 * @author tre2e
 * @date 2025-03-10
 */
public class SysInviteCode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 邀请码ID */
    private Long id;

    /** 邀请码，唯一标识 */
    @Excel(name = "邀请码，唯一标识")
    private String inviteCode;

    /** 是否已使用（0未使用，1已使用） */
    @Excel(name = "是否已使用", readConverterExp = "0=未使用，1已使用")
    private Integer isUsed;

    /** 使用者的用户ID */
    @Excel(name = "使用者的用户ID")
    private Long usedBy;

    /** 使用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "使用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date usedTime;

    /** 过期时间，NULL表示不过期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "过期时间，NULL表示不过期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    // 用于显示
    private String userName; // 新增字段

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setInviteCode(String inviteCode) 
    {
        this.inviteCode = inviteCode;
    }

    public String getInviteCode() 
    {
        return inviteCode;
    }
    public void setIsUsed(Integer isUsed) 
    {
        this.isUsed = isUsed;
    }

    public Integer getIsUsed() 
    {
        return isUsed;
    }
    public void setUsedBy(Long usedBy) 
    {
        this.usedBy = usedBy;
    }

    public Long getUsedBy() 
    {
        return usedBy;
    }
    public void setUsedTime(Date usedTime) 
    {
        this.usedTime = usedTime;
    }

    public Date getUsedTime() 
    {
        return usedTime;
    }
    public void setExpireTime(Date expireTime) 
    {
        this.expireTime = expireTime;
    }

    public Date getExpireTime() 
    {
        return expireTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inviteCode", getInviteCode())
            .append("isUsed", getIsUsed())
            .append("usedBy", getUsedBy())
            .append("usedTime", getUsedTime())
            .append("expireTime", getExpireTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
