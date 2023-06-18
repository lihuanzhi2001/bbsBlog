package com.easybbs.entity.query;

import lombok.Data;

/**
 * @author lihuanzhi
 * @since 2023/4/24 10:20
 */
@Data
public class ImgCategoryQuery extends BaseParam {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 名字
     */
    private String name;

    private String nameFuzzy;

    /**
     * 排序字段
     */
    private Integer order;
}
