package com.easybbs.entity.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/21 11:07
 */
public enum UserPermissionEnum {

    POST_ARTICLE("postArticle", "编辑发发布文章"),
    POST_COMMENT("postComment", "发表评论"),
    POST_IMG("postImg", "发布图片"),
    UN_FREEZE_IMG("unFreezeImg", "解冻图片");

    private String code;
    private String desc;

    UserPermissionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public static UserPermissionEnum getByCode(String code) {
        for (UserPermissionEnum item : UserPermissionEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static List<UserPermissionEnum> getAllItem() {
        List<UserPermissionEnum> list = new ArrayList<>();
        for (UserPermissionEnum item : UserPermissionEnum.values()) {
            list.add(item);
        }
        return list;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
