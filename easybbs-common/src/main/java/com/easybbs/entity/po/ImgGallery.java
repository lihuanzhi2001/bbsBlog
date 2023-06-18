package com.easybbs.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 图库表
 *
 * @author lihuanzhi
 * @since 2023/4/24 10:08
 */
@Data
public class ImgGallery implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 图片地址
     */
    private String img;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 状态(0-未审核, 1-已审核)
     */
    private Integer status;

    /**
     * 信息状态(0-隐藏, 1-展示)
     */
    private Integer status2;

    /**
     * 冻结状态(0-未冻结, 1-已冻结)
     */
    private Integer status3;

}
