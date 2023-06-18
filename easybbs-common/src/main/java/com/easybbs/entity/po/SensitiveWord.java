package com.easybbs.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 敏感词
 *
 * @author lihuanzhi
 * @since 2023/4/11 9:23
 */
@Data
public class SensitiveWord implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 敏感词
     */
    private String word;

    /**
     * 状态：0-使用，1-使用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
