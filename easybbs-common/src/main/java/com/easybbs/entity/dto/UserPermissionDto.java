package com.easybbs.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/20 10:09
 */
@Data
public class UserPermissionDto {

//    /**
//     * 是否可以发布文章
//     */
//    private Boolean postArticle;
//
//    /**
//     * 是否可以发表评论
//     */
//    private Boolean postComment;

    // 权限列表
    private List<String> permissions;
}
