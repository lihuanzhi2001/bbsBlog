package com.easybbs.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lihuanzhi
 * @since 2023/3/30 17:59
 */
@Data
public class ComplaintRecord implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 投诉用户的userId
     */
    private String userId;

    /**
     * 主体id
     */
    private String objectId;

    /**
     * 投诉类型(0: 博文投诉, 1: 评论投诉, 2: 图片投诉)
     */
    private Integer type;

    /**
     * 投诉内容
     */
    private String content;

    /**
     * 投诉时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 投诉处理状态(0 - 未处理，1 - 处理中，2 - 已处理，3 - 已关闭)
     */
    private Integer status;

}
