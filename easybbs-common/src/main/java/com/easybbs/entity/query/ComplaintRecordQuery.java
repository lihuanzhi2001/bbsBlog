package com.easybbs.entity.query;

import lombok.Data;

/**
 * @author lihuanzhi
 * @since 2023/3/30 20:07
 */
@Data
public class ComplaintRecordQuery extends BaseParam {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 投诉用户id
     */
    private String userId;

    private String userIdFuzzy;

    /**
     * 主体id
     */
    private String objectId;

    private String objectIdFuzzy;

    /**
     * 投诉类型(0: 博文投诉, 1: 评论投诉)
     */
    private Integer type;


    /**
     * 投诉内容
     */
    private String content;

    private String contentFuzzy;


    /**
     * 创建时间(投诉时间)
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 投诉处理状态(0 - 未处理，1 - 处理中，2 - 已处理，3 - 已关闭)
     */
    private Integer status;
}
