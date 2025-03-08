package com.ruoyi.manage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户借阅对象 book_issue
 * 
 * @author tre2e
 * @date 2025-03-07
 */
public class BookIssue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 借阅ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 书籍ID */
    @Excel(name = "书籍ID")
    private Long bookId;

    /** 借阅日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借阅日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issueDate;

    /** 应还日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "应还日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 归还日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "归还日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnDate;

    /** 状态（0未归还 1已归还） */
    @Excel(name = "状态", readConverterExp = "0=未归还,1=已归还")
    private Integer status;

    /* 新增字段显示状态内容 */
    private String statusName;   // 状态名称
    // 查询条件字段
    private String userName;
    private String bookName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setBookId(Long bookId) 
    {
        this.bookId = bookId;
    }

    public Long getBookId() 
    {
        return bookId;
    }
    public void setIssueDate(Date issueDate) 
    {
        this.issueDate = issueDate;
    }

    public Date getIssueDate() 
    {
        return issueDate;
    }
    public void setDueDate(Date dueDate) 
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate() 
    {
        return dueDate;
    }
    public void setReturnDate(Date returnDate) 
    {
        this.returnDate = returnDate;
    }

    public Date getReturnDate() 
    {
        return returnDate;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("bookId", getBookId())
            .append("issueDate", getIssueDate())
            .append("dueDate", getDueDate())
            .append("returnDate", getReturnDate())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("remark", getRemark())
            .toString();
    }
}
