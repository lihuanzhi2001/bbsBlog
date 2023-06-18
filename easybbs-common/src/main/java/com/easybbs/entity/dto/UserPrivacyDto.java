package com.easybbs.entity.dto;

import lombok.Data;

/**
 * @author lihuanzhi
 * @since 2023/4/20 10:09
 */
@Data
public class UserPrivacyDto {

    /**
     * 文章列表(0:所有人看见 1:粉丝可见 2:所有人不可见)
     */
    private Integer articleView;

    /**
     * 评论列表(0:所有人看见 1:粉丝可见 2:所有人不可见)
     */
    private Integer commentView;

    /**
     * 点赞列表(0:所有人看见 1:粉丝可见 2:所有人不可见)
     */
    private Integer likeView;

    /**
     * 收藏夹列表(0:所有人看见 1:粉丝可见 2:所有人不可见)
     */
    private Integer collectView;

    /**
     * 关注列表(0:所有人看见 1:粉丝可见 2:所有人不可见)
     */
    private Integer followView;

    /**
     * 粉丝列表(0:所有人看见 1:粉丝可见 2:所有人不可见)
     */
    private Integer fansView;
}
