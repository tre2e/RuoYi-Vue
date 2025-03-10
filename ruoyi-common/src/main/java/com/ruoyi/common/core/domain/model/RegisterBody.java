package com.ruoyi.common.core.domain.model;

/**
 * 用户注册对象
 *
 * @author ruoyi
 */
public class RegisterBody extends LoginBody
{
    /**
     * 邀请码                                      //双向职责是个问题，但能跑
     */
    private String inviteCode;

    /**
     * 新创建的用户ID（注册成功后设置）
     */
    private Long userId;

    public String getInviteCode()
    {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode)
    {
        this.inviteCode = inviteCode;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RegisterBody{" +
                "inviteCode='" + inviteCode + '\'' +
                ", userId=" + userId +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", code='" + getCode() + '\'' +
                ", uuid='" + getUuid() + '\'' +
                '}';
    }
}