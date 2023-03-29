package com.easybbs.entity.vo.web;

import lombok.Data;

@Data
public class FormArticleDetailVO {
    private ForumArticleVO forumArticle;
    private ForumArticleAttachmentVo attachment;
    private Boolean haveLike = false;
    // TODO 新增已收藏flag
    private Boolean haveCollect = false;

}
