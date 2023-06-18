package com.easybbs.entity.query;

import lombok.Data;

/**
 * @author lihuanzhi
 * @since 2023/3/30 20:07
 */
@Data
public class ComplaintHistoriesRecordQuery extends BaseParam {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 投诉记录id
     */
    private Integer complaintId;

    /**
     * 处理管理员id
     */
    private String adminId;

    private String adminIdFuzzy;


    /**
     * 管理员处理内容或备注信息
     */
    private String comment;

    private String commentFuzzy;


    /**
     * 创建时间(投诉时间)
     */
    private String updateTime;

    private String updateTimeStart;

    private String updateTimeEnd;

}
