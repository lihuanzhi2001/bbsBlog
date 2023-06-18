package com.easybbs.mappers;

import com.easybbs.entity.po.Notice;
import org.apache.ibatis.annotations.Param;

/**
 * @author lihuanzhi
 * @since 2023/4/6 10:21
 */
public interface NoticeMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 根据id查询对象
     */
    Notice getNoticeById(@Param("id") Integer id);


    /**
     * 根据id修改
     */
    Integer updateNoticeById(@Param("bean") T t, @Param("id") Integer id);


    /**
     * 根据id删除
     */
    Integer deleteNoticeById(@Param("id") Integer id);


    /**
     * 根据id更新投诉记录的状态status(0 - 未发布，1 - 已发布)
     */
    Integer updateNoticeStatusById(@Param("id") Integer id, @Param("status") Integer status);
}