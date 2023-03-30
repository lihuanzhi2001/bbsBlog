package com.easybbs.service;

import com.easybbs.entity.po.FollowRecord;
import com.easybbs.entity.query.FollowRecordQuery;
import com.easybbs.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * 用户关注
 *
 * @author lihuanzhi
 * @since 2023/3/29 11:44
 */
public interface FollowRecordService {
    /**
     * 根据条件查询列表
     */
    List<FollowRecord> findListByParam(FollowRecordQuery param);

    /**
     * 根据条件查询总数
     */
    Integer findCountByParam(FollowRecordQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<FollowRecord> findListByPage(FollowRecordQuery param);

    /**
     * 新增
     */
    Integer add(FollowRecord bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<FollowRecord> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<FollowRecord> listBean);


    /**
     * 根据id查询对象
     */
    FollowRecord getUserFollowRecordById(Integer id);


    /**
     * 根据id删除
     */
    Integer deleteUserFollowRecordById(Integer id);


    /**
     * 根据UserIdAndFollowedUserId查询对象
     */
    FollowRecord getUserFollowRecordByUserIdAndFollowedUserId(String userId, String followedUserId);


    /**
     * 根据UserIdAndFollowedUserId删除
     */
    Integer deleteUserFollowRecordByUserIdAndFollowedUserId(String userId, String followedUserId);

    void doFollow(String userId, String followedUserId);

}
