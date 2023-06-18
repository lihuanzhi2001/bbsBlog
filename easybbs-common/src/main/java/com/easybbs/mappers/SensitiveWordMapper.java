package com.easybbs.mappers;

/**
 * @author lihuanzhi
 * @since 2023/4/11 9:36
 */

import com.easybbs.entity.po.SensitiveWord;
import org.apache.ibatis.annotations.Param;

public interface SensitiveWordMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 根据id查询对象
     */
    SensitiveWord getSensitiveWordById(@Param("id") Integer id);


    /**
     * 根据id修改
     */
    Integer updateSensitiveWordById(@Param("bean") T t, @Param("id") Integer id);


    /**
     * 根据id删除
     */
    Integer deleteSensitiveWordById(@Param("id") Integer id);


    /**
     * 根据id更新敏感词的状态status(0 - 未使用，1 - 使用)
     */
    Integer updateSensitiveWordStatusById(@Param("id") Integer id, @Param("status") Integer status);
}