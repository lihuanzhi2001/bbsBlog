package com.easybbs.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 收藏记录操作dao
 *
 * @author lihuanzhi
 * @since 2023/3/28 15:05
 */
public interface CollectRecordMapper<T, P> extends BaseMapper<T, P> {
    /**
     * 根据OpId更新
     */
    Integer updateByOpId(@Param("bean") T t, @Param("opId") Integer opId);


    /**
     * 根据OpId删除
     */
    Integer deleteByOpId(@Param("opId") Integer opId);


    /**
     * 根据OpId获取对象
     */
    T selectByOpId(@Param("opId") Integer opId);


    /**
     * 根据ObjectIdAndUserId更新
     */
    Integer updateByObjectIdAndUserId(@Param("bean") T t, @Param("objectId") String objectId, @Param("userId") String userId);


    /**
     * 根据ObjectIdAndUserId删除
     */
    Integer deleteByObjectIdAndUserId(@Param("objectId") String objectId, @Param("userId") String userId);


    /**
     * 根据ObjectIdAndUserId获取对象
     */
    T selectByObjectIdAndUserId(@Param("objectId") String objectId, @Param("userId") String userId);


}
