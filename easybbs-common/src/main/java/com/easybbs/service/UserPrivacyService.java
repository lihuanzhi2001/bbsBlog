package com.easybbs.service;

import com.easybbs.entity.po.UserPrivacy;
import com.easybbs.entity.query.UserPrivacyQuery;

import java.util.List;

/**
 * 用户隐私设置
 *
 * @author lihuanzhi
 * @since 2023/4/20 10:05
 */
public interface UserPrivacyService {
    /**
     * 根据条件查询列表
     */
    List<UserPrivacy> findListByParam(UserPrivacyQuery param);

    /**
     * 根据条件查询总数
     */
    Integer findCountByParam(UserPrivacyQuery param);

    /**
     * 新增
     */
    Integer add(UserPrivacy bean);

    /**
     * 根据userId查询对象
     */
    UserPrivacy getUserPrivacyByUserId(String userId);


    /**
     * 根据userId修改
     */
    Integer updateUserPrivacyByUserId(UserPrivacy bean, String UserId);


    /**
     * 根据userId删除
     */
    Integer deletUserPrivacyByUserId(String UserId);
}
