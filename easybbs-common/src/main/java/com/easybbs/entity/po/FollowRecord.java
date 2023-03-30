package com.easybbs.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 关注记录表
 *
 * @author lihuanzhi
 * @since 2023/3/29 11:26
 */
@Data
public class FollowRecord implements Serializable {

    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 用户ID(粉丝id)
     */
    private String userId;

    /**
     * 关注id
     */
    private String followedUserId;

    /**
     * 创建时间(关注时间)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
