package com.easybbs.entity.enums;

/**
 * @author lihuanzhi
 * @since 2023/4/24 13:49
 */
public enum ImgGalleryStatusEnum {
    NO_AUDIT(0, "未审核"),
    AUDIT(1, "已审核"),
    ERROR_AUDIT(2, "审核未通过"),
    INFO_SHOW(1, "信息展示"),
    INFO_HIDE(0, "信息隐藏"),
    FREEZE(1, "已冻结"),
    UN_FREEZE(0, "未冻结");

    private Integer code;

    private String desc;

    ImgGalleryStatusEnum(Integer code, String msg) {
        this.code = code;
        this.desc = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
