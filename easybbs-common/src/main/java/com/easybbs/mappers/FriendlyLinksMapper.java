package com.easybbs.mappers;

import com.easybbs.entity.po.FriendlyLinks;
import org.apache.ibatis.annotations.Param;

public interface FriendlyLinksMapper<T, P> extends BaseMapper<T, P> {
    /**
     * 根据id查询对象
     */
    FriendlyLinks getFriendlyLinksById(@Param("id") Integer id);


    /**
     * 根据id修改
     */
    Integer updateFriendlyLinksById(@Param("bean") T t, @Param("id") Integer id);


    /**
     * 根据id删除
     */
    Integer deleteFriendlyLinksById(@Param("id") Integer id);


    /**
     * 根据id更新投诉记录的状态status(0 - 未发布，1 - 已发布)
     */
    Integer updateFriendlyLinksStatusById(@Param("id") Integer id, @Param("status") Integer status);
}
