package com.easybbs.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 图片分类
 *
 * @author lihuanzhi
 * @since 2023/4/24 10:07
 */
@Data
public class ImgCategory implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 名字
     */
    private String name;

    /**
     * 排序字段
     */
    private Integer order;
}
