package com.easybbs.entity.dto;

import com.easybbs.annotation.VerifyParam;

public class SysSetting4EmailDto {

    //邮件标题
    @VerifyParam(required = true)
    private String emailTitle;

    //邮件内容
    @VerifyParam(required = true)
    private String emailContent;

    // 邮件服务器地址
    private String mailHost;

    // 邮件服务器端口
    private Integer mailPort;

    // 邮件服务器用户名
    private String mailUsername;

    // 邮件服务器密码
    private String mailPassword;


    public String getEmailTitle() {
        return emailTitle;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public Integer getMailPort() {
        return mailPort;
    }

    public void setMailPort(Integer mailPort) {
        this.mailPort = mailPort;
    }

    public String getMailUsername() {
        return mailUsername;
    }

    public void setMailUsername(String mailUsername) {
        this.mailUsername = mailUsername;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }
}
