package com.easybbs.entity.vo.web;

import lombok.Data;

/**
 * @author lihuanzhi
 * @since 2023/4/24 19:06
 */
@Data
public class ImgGalleryVo {
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
     * 状态(0-未审核, 1-已审核, 2-已冻结)
     */
    private Integer status;

    /**
     * 信息状态(0-隐藏, 1-展示)
     */
    private Integer status2;

    private Boolean show;
}
