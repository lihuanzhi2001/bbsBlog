package com.easybbs.entity.query;

import lombok.Data;

/**
 * 关注记录参数
 *
 * @author lihuanzhi
 * @since 2023/3/29 11:35
 */
@Data
public class FollowRecordQuery extends BaseParam {

    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 用户ID(粉丝id)
     */
    private String userId;

    private String userIdFuzzy;

    /**
     * 关注用户的ID
     */
    private String followedUserId;

    private String followedUserIdFuzzy;


    /**
     * 创建时间(关注时间)
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

}
