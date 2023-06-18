package com.easybbs.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @author lihuanzhi
 * @since 2023/4/20 10:22
 */
public interface UserPrivacyMapper<T, P> extends BaseMapper<T, P> {
    /**
     * 根据userId更新
     */
    Integer updateByUserId(@Param("bean") T t, @Param("userId") String userId);


    /**
     * 根据userId删除
     */
    Integer deleteByUserId(@Param("userId") String userId);


    /**
     * 根据userId获取对象
     */
    T selectByUserId(@Param("userId") String userId);
}
