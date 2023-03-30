package com.easybbs.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 用户关注 dao
 *
 * @author lihuanzhi
 * @since 2023/3/29 11:46
 */
public interface FollowRecordMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 根据id删除
     */
    Integer deleteById(@Param("id") Integer id);


    /**
     * 根据id获取对象
     */
    T selectById(@Param("id") Integer id);


    /**
     * 根据userIdAndFollowedUserId删除
     */
    Integer deleteByUserIdAndFollowedUserId(@Param("userId") String userId, @Param("followedUserId") String followedUserId);


    /**
     * 根据userIdAndFollowedUserId获取对象
     */
    T selectByUserIdAndFollowedUserId(@Param("userId") String userId, @Param("followedUserId") String followedUserId);

}
