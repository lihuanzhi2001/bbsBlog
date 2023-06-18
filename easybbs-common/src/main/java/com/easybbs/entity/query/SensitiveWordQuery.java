package com.easybbs.entity.query;

import lombok.Data;

/**
 * @author lihuanzhi
 * @since 2023/4/11 9:30
 */
@Data
public class SensitiveWordQuery extends BaseParam {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 敏感词
     */
    private String word;

    private String wordFuzzy;

    /**
     * 状态：0-使用，1-使用
     */
    private Integer status;


    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

}
