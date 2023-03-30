package com.easybbs.entity.query;

import lombok.Data;

/**
 * 收藏记录参数
 *
 * @author lihuanzhi
 * @since 2023/3/28 14:56
 */

@Data
public class CollectRecordQuery extends BaseParam {
    /**
     * 自增ID
     */
    private Integer opId;


    /**
     * 主体ID
     */
    private String objectId;

    private String objectIdFuzzy;

    /**
     * 用户ID
     */
    private String userId;

    private String userIdFuzzy;

    /**
     * 发布时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     *  作者id
     */
    private String authorUserId;

    private String authorUserIdFuzzy;
}
