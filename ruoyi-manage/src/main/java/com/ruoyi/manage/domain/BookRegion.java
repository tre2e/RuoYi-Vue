package com.ruoyi.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 区域管理对象 book_region
 * 
 * @author tre2e
 * @date 2025-03-05
 */
public class BookRegion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 区域ID */
    private Long id;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String name;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("remark", getRemark())
            .toString();
    }
}
