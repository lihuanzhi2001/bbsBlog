package com.easybbs.service.impl;

import com.easybbs.entity.po.UserPermission;
import com.easybbs.entity.query.UserPermissionQuery;
import com.easybbs.mappers.UserPermissionMapper;
import com.easybbs.service.UserPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/20 10:07
 */
@Service("userPermission")
public class UserPermissionServiceImpl implements UserPermissionService {

    @Resource
    private UserPermissionMapper<UserPermission, UserPermissionQuery> userPermissionMapper;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<UserPermission> findListByParam(UserPermissionQuery param) {
        return this.userPermissionMapper.selectList(param);
    }

    /**
     * 根据条件查询总数
     */
    @Override
    public Integer findCountByParam(UserPermissionQuery param) {
        return this.userPermissionMapper.selectCount(param);
    }

    /**
     * 新增
     *
     * @param bean
     * @return
     */
    @Override
    public Integer add(UserPermission bean) {
        return this.userPermissionMapper.insert(bean);
    }

    /**
     * 根据userId获取
     *
     * @param userId
     * @return
     */
    @Override
    public UserPermission getUserPermissionByUserId(String userId) {
        return this.userPermissionMapper.selectByUserId(userId);
    }

    /**
     * 根据userId更新
     *
     * @param bean
     * @param userId
     * @return
     */
    @Override
    public Integer updateUserPermissionByUserId(UserPermission bean, String userId) {
        return this.userPermissionMapper.updateByUserId(bean, userId);
    }

    /**
     * 根据userId删除
     *
     * @param userId
     * @return
     */
    @Override
    public Integer deletUserPermissionByUserId(String userId) {
        return this.userPermissionMapper.deleteByUserId(userId);
    }
}
