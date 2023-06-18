package com.easybbs.entity.dto;

import com.easybbs.annotation.VerifyParam;

public class SysSetting4AuditDto {
    /**
     * 帖子是否需要审核
     */
    @VerifyParam(required = true)
    private Boolean postAudit;
    /**
     * 评论是否需要审核
     */
    @VerifyParam(required = true)
    private Boolean commentAudit;

    /**
     * 图片是否需要审核
     *
     * @return
     */
    @VerifyParam(required = true)
    private Boolean imgAudit;

    public Boolean getPostAudit() {
        return postAudit;
    }

    public void setPostAudit(Boolean postAudit) {
        this.postAudit = postAudit;
    }

    public Boolean getCommentAudit() {
        return commentAudit;
    }

    public void setCommentAudit(Boolean commentAudit) {
        this.commentAudit = commentAudit;
    }

    public void setImgAudit(Boolean imgAudit) {
        this.imgAudit = imgAudit;
    }

    public Boolean getImgAudit() {
        return imgAudit;
    }
}
