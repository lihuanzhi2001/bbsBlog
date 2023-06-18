package com.easybbs.service.impl;

import com.easybbs.entity.po.UserPrivacy;
import com.easybbs.entity.query.UserPrivacyQuery;
import com.easybbs.mappers.UserPrivacyMapper;
import com.easybbs.service.UserPrivacyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/20 10:08
 */
@Service("userPrivacy")
public class UserPrivacyServiceImpl implements UserPrivacyService {

    @Resource
    private UserPrivacyMapper<UserPrivacy, UserPrivacyQuery> userPrivacyMapper;

    /**
     * 根据条件查询列表
     *
     * @param param
     * @return
     */
    @Override
    public List<UserPrivacy> findListByParam(UserPrivacyQuery param) {
        return this.userPrivacyMapper.selectList(param);
    }

    /**
     * 根据条件查询总数
     *
     * @param param
     * @return
     */
    @Override
    public Integer findCountByParam(UserPrivacyQuery param) {
        return this.userPrivacyMapper.selectCount(param);
    }

    /**
     * 新增
     *
     * @param bean
     * @return
     */
    @Override
    public Integer add(UserPrivacy bean) {
        return this.userPrivacyMapper.insert(bean);
    }

    /**
     * 根据userId获取
     *
     * @param userId
     * @return
     */
    @Override
    public UserPrivacy getUserPrivacyByUserId(String userId) {
        return this.userPrivacyMapper.selectByUserId(userId);
    }

    /**
     * 根据userId更新
     *
     * @param bean
     * @param userId
     * @return
     */
    @Override
    public Integer updateUserPrivacyByUserId(UserPrivacy bean, String userId) {
        return this.userPrivacyMapper.updateByUserId(bean, userId);
    }

    /**
     * 根据userId删除
     *
     * @param userId
     * @return
     */
    @Override
    public Integer deletUserPrivacyByUserId(String userId) {
        return this.userPrivacyMapper.deleteByUserId(userId);
    }
}
