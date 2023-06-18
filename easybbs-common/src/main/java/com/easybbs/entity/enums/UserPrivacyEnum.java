package com.easybbs.entity.enums;

/**
 * 用户隐私设置枚举类
 *
 * @author lihuanzhi
 * @since 2023/4/20 11:15
 */
public enum UserPrivacyEnum {

    EVERYONE_VISIBLE(0, "所有人可见"),
    FANS_VISIBLE(1, "粉丝可见"),
    EVERY_INVISIBLE(2, "所有人不可见");

    private Integer code;
    private String desc;

    UserPrivacyEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
