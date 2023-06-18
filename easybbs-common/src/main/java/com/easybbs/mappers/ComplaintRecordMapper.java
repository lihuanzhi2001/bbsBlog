package com.easybbs.mappers;

import com.easybbs.entity.po.ComplaintRecord;
import org.apache.ibatis.annotations.Param;

/**
 * @author lihuanzhi
 * @since 2023/3/31 9:30
 */
public interface ComplaintRecordMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 根据id查询对象
     */
    ComplaintRecord getComplaintRecordById(@Param("id") Integer id);


    /**
     * 根据id修改
     */
    Integer updateComplaintRecordById(@Param("bean") T t, @Param("id") Integer id);


    /**
     * 根据id删除
     */
    Integer deleteComplaintRecordById(@Param("id") Integer id);


    /**
     * 根据id更新投诉记录的状态status(0 - 未处理，1 - 处理中，2 - 已处理，3 - 已关闭)
     */
    Integer updateComplaintStatusById(@Param("id") Integer id, @Param("status") Integer status);
}
