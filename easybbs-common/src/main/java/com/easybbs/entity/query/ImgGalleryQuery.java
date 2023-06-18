package com.easybbs.entity.query;

import lombok.Data;

/**
 * @author lihuanzhi
 * @since 2023/4/24 10:20
 */
@Data
public class ImgGalleryQuery extends BaseParam {
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

    private String categoryNameFuzzy;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    private String nickNameFuzzy;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 更新时间
     */
    private String updateTime;

    private String updateTimeStart;

    private String updateTimeEnd;

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
