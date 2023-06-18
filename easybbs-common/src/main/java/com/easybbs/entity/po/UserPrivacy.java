package com.easybbs.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户隐私设置
 *
 * @author lihuanzhi
 * @since 2023/4/20 9:48
 */
@Data
public class UserPrivacy implements Serializable {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户权限设置json串
     */
    private String jsonPrivacy;
}
