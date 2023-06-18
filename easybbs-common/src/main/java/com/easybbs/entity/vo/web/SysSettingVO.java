package com.easybbs.entity.vo.web;

public class SysSettingVO {
    private Boolean commentOpen;

    private Boolean noAuditPost;

    public Boolean getCommentOpen() {
        return commentOpen;
    }

    public void setCommentOpen(Boolean commentOpen) {
        this.commentOpen = commentOpen;
    }

    public Boolean getNoAuditPost() {
        return noAuditPost;
    }

    public void setNoAuditPost(Boolean noAuditPost) {
        this.noAuditPost = noAuditPost;
    }
}
