package com.easybbs.entity.query;

import lombok.Data;

/**
 * @author lihuanzhi
 * @since 2023/4/20 9:52
 */
@Data

public class UserPermissionQuery extends BaseParam {
    /**
     * 用户id
     */
    private String userId;
    private String userIdFuzzy;

    /**
     * 用户权限设置json串
     */
    private String jsonPermission;
    private String jsonPermissionFuzzy;
}
