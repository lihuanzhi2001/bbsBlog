package com.easybbs.entity.vo.web;

import lombok.Data;

/**
 * @author lihuanzhi
 * @since 2023/3/30 14:34
 */
@Data
public class UserFollowVo {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 个人描述
     */
    private String personDescription;

    /**
     * 当前登录用户是否已关注flag(默认为未关注)
     */
    private Boolean haveFollow = false;
}
