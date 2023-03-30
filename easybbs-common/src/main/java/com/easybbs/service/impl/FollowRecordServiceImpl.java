package com.easybbs.service.impl;

import com.easybbs.entity.enums.PageSize;
import com.easybbs.entity.po.FollowRecord;
import com.easybbs.entity.po.UserInfo;
import com.easybbs.entity.query.FollowRecordQuery;
import com.easybbs.entity.query.SimplePage;
import com.easybbs.entity.query.UserInfoQuery;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.exception.BusinessException;
import com.easybbs.mappers.FollowRecordMapper;
import com.easybbs.mappers.UserInfoMapper;
import com.easybbs.service.FollowRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/3/29 11:45
 */
@Service("followRecordService")
public class FollowRecordServiceImpl implements FollowRecordService {

    @Resource
    private FollowRecordMapper<FollowRecord, FollowRecordQuery> followRecordMapper;

    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;


    /**
     * 根据条件查询列表
     *
     * @param param
     * @return
     */
    @Override
    public List<FollowRecord> findListByParam(FollowRecordQuery param) {
        return this.followRecordMapper.selectList(param);
    }

    /**
     * 根据条件查询总数
     *
     * @param param
     * @return
     */
    @Override
    public Integer findCountByParam(FollowRecordQuery param) {
        return this.followRecordMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     *
     * @param param
     * @return
     */
    @Override
    public PaginationResultVO<FollowRecord> findListByPage(FollowRecordQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<FollowRecord> list = this.findListByParam(param);
        PaginationResultVO<FollowRecord> result = new PaginationResultVO<>(count, page.getPageSize(),
                page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     *
     * @param bean
     * @return
     */
    @Override
    public Integer add(FollowRecord bean) {
        return this.followRecordMapper.insert(bean);
    }

    /**
     * 批量新增
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addBatch(List<FollowRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.followRecordMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     *
     * @param listBean
     * @return
     */
    @Override
    public Integer addOrUpdateBatch(List<FollowRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.followRecordMapper.insertOrUpdateBatch(listBean);
    }


    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    @Override
    public FollowRecord getUserFollowRecordById(Integer id) {
        return this.followRecordMapper.selectById(id);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteUserFollowRecordById(Integer id) {
        return this.followRecordMapper.deleteById(id);
    }

    /**
     * 根据userIdAndFollowedUserId获取对象
     *
     * @param userId
     * @param followedUserId
     * @return
     */
    @Override
    public FollowRecord getUserFollowRecordByUserIdAndFollowedUserId(String userId, String followedUserId) {
        return this.followRecordMapper.selectByUserIdAndFollowedUserId(userId, followedUserId);
    }

    /**
     * 根据userIdAndFollowedUserId删除
     *
     * @param userId
     * @param followedUserId
     * @return
     */
    @Override
    public Integer deleteUserFollowRecordByUserIdAndFollowedUserId(String userId, String followedUserId) {
        return this.followRecordMapper.deleteByUserIdAndFollowedUserId(userId, followedUserId);
    }

    /**
     * 关注功能（TODO 暂时没加消息通知功能，后续可能会添加。。。）
     *
     * @param userId
     * @param followedUserId
     */
    @Override
    public void doFollow(String userId, String followedUserId) {
        FollowRecord followRecord = userFollow(userId, followedUserId);
        // TODO 这里可能后续会添加消息通知功能。。。
    }

    /**
     * 用户关注，取消关注
     *
     * @param userId
     * @param followedUserId
     * @return
     */
    public FollowRecord userFollow(String userId, String followedUserId) {
        FollowRecord followRecord = this.followRecordMapper.selectByUserIdAndFollowedUserId(userId, followedUserId);

        if (followRecord != null) {
            this.followRecordMapper.deleteByUserIdAndFollowedUserId(userId, followedUserId);
        } else {
            UserInfo userInfo = userInfoMapper.selectByUserId(followedUserId);
            if (userInfo == null) {
                throw new BusinessException("关注的用户不存在");
            }
            FollowRecord userFollow = new FollowRecord();
            userFollow.setUserId(userId);
            userFollow.setFollowedUserId(followedUserId);
            userFollow.setCreateTime(new Date());
            this.followRecordMapper.insert(userFollow);
        }
        return followRecord;

    }


}
