package com.ruoyi.manage.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

public class BookIssueVo {

    private Long id;

    @Excel(name = "用户名")
    private String userName; // 新增用户名字段

    @Excel(name = "书籍名")
    private String bookName; // 新增书籍名字段

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借阅日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "应还日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "归还日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnDate;

    @Excel(name = "状态", readConverterExp = "0=未归还,1=已归还")
    private Integer status;

    // 新增 statusName 字段
    @Excel(name = "状态名称")
    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    private String remark;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }
    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}