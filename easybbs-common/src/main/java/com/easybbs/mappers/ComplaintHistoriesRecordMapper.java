package com.easybbs.mappers;

import com.easybbs.entity.po.ComplaintHistoriesRecord;
import org.apache.ibatis.annotations.Param;

/**
 * @author lihuanzhi
 * @since 2023/3/31 9:30
 */
public interface ComplaintHistoriesRecordMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 根据id查询对象
     */
    ComplaintHistoriesRecord getComplaintHistoriesById(@Param("id") Integer id);


    /**
     * 根据id修改
     */
    Integer updateComplaintHistoriesById(@Param("bean") T t, @Param("id") Integer id);


    /**
     * 根据id删除
     */
    Integer deleteComplaintHistoriesById(@Param("id") Integer id);

}
