package com.easybbs.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 友情链接
 */
@Data
public class FriendlyLinks implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 图标icon
     */
    private String icon;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String desc;

    /**
     * 跳转地址
     */
    private String address;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 状态
     */
    private Integer status;
}
