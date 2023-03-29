package com.easybbs.service;

import com.easybbs.entity.po.CollectRecord;
import com.easybbs.entity.query.CollectRecordQuery;
import com.easybbs.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/3/28 14:53
 */
public interface CollectRecordService {

    /**
     * 根据条件查询列表
     */
    List<CollectRecord> findListByParam(CollectRecordQuery param);

    /**
     * 根据条件查询列表
     */
    Integer findCountByParam(CollectRecordQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<CollectRecord> findListByPage(CollectRecordQuery param);

    /**
     * 新增
     */
    Integer add(CollectRecord bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<CollectRecord> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<CollectRecord> listBean);

    /**
     * 根据OpId查询对象
     */
    CollectRecord getUserOperRecordByOpId(Integer opId);


    /**
     * 根据OpId修改
     */
    Integer updateUserOperRecordByOpId(CollectRecord bean, Integer opId);


    /**
     * 根据OpId删除
     */
    Integer deleteUserOperRecordByOpId(Integer opId);


    /**
     * 根据ObjectIdAndUserId查询对象
     */
    CollectRecord getUserOperRecordByObjectIdAndUserId(String objectId, String userId);


    /**
     * 根据ObjectIdAndUserId修改
     */
    Integer updateUserOperRecordByObjectIdAndUserId(CollectRecord bean, String objectId, String userId);


    /**
     * 根据ObjectIdAndUserId删除
     */
    Integer deleteUserOperRecordByObjectIdAndUserId(String objectId, String userId);

    void doCollect(String objectId, String userId, String nickName);
}
