package com.easybbs.service;

import com.easybbs.entity.po.UserPermission;
import com.easybbs.entity.query.UserPermissionQuery;

import java.util.List;

/**
 * 用户权限设置
 *
 * @author lihuanzhi
 * @since 2023/4/20 9:58
 */
public interface UserPermissionService {
    /**
     * 根据条件查询列表
     */
    List<UserPermission> findListByParam(UserPermissionQuery param);

    /**
     * 根据条件查询总数
     */
    Integer findCountByParam(UserPermissionQuery param);

    /**
     * 新增
     */
    Integer add(UserPermission bean);

    /**
     * 根据userId查询对象
     */
    UserPermission getUserPermissionByUserId(String userId);


    /**
     * 根据userId修改
     */
    Integer updateUserPermissionByUserId(UserPermission bean, String userId);


    /**
     * 根据userId删除
     */
    Integer deletUserPermissionByUserId(String userId);


}
