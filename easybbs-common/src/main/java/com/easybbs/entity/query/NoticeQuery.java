package com.easybbs.entity.query;


import lombok.Data;

/**
 * @author lihuanzhi
 * @since 2023/4/4 17:31
 */
@Data
public class NoticeQuery extends BaseParam {

    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 公告标题
     */
    private String title;

    private String titleFuzzy;

    /**
     * 公告内容
     */
    private String content;

    private String contentFuzzy;


    /**
     * 公告状态：0-未发布，1-已发布
     */
    private Integer status;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 更新时间
     */
    private String updateTime;

    private String updateTimeStart;

    private String updateTimeEnd;

}
