package com.easybbs.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏记录
 *
 * @author lihuanzhi
 * @since 2023/3/28 15:02
 */
@Data
public class CollectRecord implements Serializable {


    /**
     * 自增ID
     */
    private Integer opId;


    /**
     * 主体ID
     */
    private String objectId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 作者id
     */
    private String authorUserId;
}
