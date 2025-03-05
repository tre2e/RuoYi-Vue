package com.ruoyi.manage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 书籍对象 mng_book
 * 
 * @author tre2e
 * @date 2025-03-05
 */
public class Book extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 书籍ID */
    private Long id;

    /** 书籍名称 */
    @Excel(name = "书籍名称")
    private String name;

    /** 封面 */
    @Excel(name = "封面")
    private String cover;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 出版社 */
    @Excel(name = "出版社")
    private String publisher;

    /** ISBN编号 */
    @Excel(name = "ISBN编号")
    private String isbn;

    /** 出版日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出版日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishDate;

    /** 类别ID */
    @Excel(name = "类别ID")
    private Long categoryId;

    /** 区域ID */
    @Excel(name = "区域ID")
    private Long regionId;

    /** 书籍数量 */
    @Excel(name = "书籍数量")
    private Long quantity;

    /** 状态（0不可借 1可借 2未到馆） */
    @Excel(name = "状态", readConverterExp = "0=不可借,1=可借,2=未到馆")
    private Integer status;

    /** 入馆时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入馆时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entryDate;

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
    public void setCover(String cover) 
    {
        this.cover = cover;
    }

    public String getCover() 
    {
        return cover;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }
    public void setPublisher(String publisher) 
    {
        this.publisher = publisher;
    }

    public String getPublisher() 
    {
        return publisher;
    }
    public void setIsbn(String isbn) 
    {
        this.isbn = isbn;
    }

    public String getIsbn() 
    {
        return isbn;
    }
    public void setPublishDate(Date publishDate) 
    {
        this.publishDate = publishDate;
    }

    public Date getPublishDate() 
    {
        return publishDate;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setRegionId(Long regionId) 
    {
        this.regionId = regionId;
    }

    public Long getRegionId() 
    {
        return regionId;
    }
    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setEntryDate(Date entryDate) 
    {
        this.entryDate = entryDate;
    }

    public Date getEntryDate() 
    {
        return entryDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("cover", getCover())
            .append("author", getAuthor())
            .append("publisher", getPublisher())
            .append("isbn", getIsbn())
            .append("publishDate", getPublishDate())
            .append("categoryId", getCategoryId())
            .append("regionId", getRegionId())
            .append("quantity", getQuantity())
            .append("status", getStatus())
            .append("entryDate", getEntryDate())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("remark", getRemark())
            .toString();
    }
}
