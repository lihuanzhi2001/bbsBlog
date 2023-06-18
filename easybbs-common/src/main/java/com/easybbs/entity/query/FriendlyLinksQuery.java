package com.easybbs.entity.query;

import lombok.Data;

@Data
public class FriendlyLinksQuery extends BaseParam {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 图标icon
     */
    private String icon;

    private String iconFuzzy;

    /**
     * 标题
     */
    private String title;

    private String titleFuzzy;

    /**
     * 描述
     */
    private String desc;

    private String descFuzzy;

    /**
     * 跳转地址
     */
    private String address;

    private String addressFuzzy;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 状态
     */
    private Integer status;
}
