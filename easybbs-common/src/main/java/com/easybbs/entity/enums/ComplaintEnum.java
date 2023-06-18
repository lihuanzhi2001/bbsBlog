package com.easybbs.entity.enums;


public enum ComplaintEnum {
    NO_HANDLE(0, "未处理"),
    HANDLEING(1, "处理中"),
    OK_HANDLE(2, "已处理"),
    CLOSE(3, "已关闭");


    private Integer status;
    private String desc;

    ComplaintEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static ArticleStatusEnum getByStatus(Integer status) {
        for (ArticleStatusEnum item : ArticleStatusEnum.values()) {
            if (item.getStatus().equals(status)) {
                return item;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
