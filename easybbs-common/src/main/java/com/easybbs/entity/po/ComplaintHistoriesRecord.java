package com.easybbs.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lihuanzhi
 * @since 2023/3/30 18:00
 */
@Data
public class ComplaintHistoriesRecord implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 投诉记录id
     */
    private Integer complaintId;

    /**
     * 处理管理员id
     */
    private String adminId;


    /**
     * 管理处理内容或备注信息
     */
    private String comment;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
