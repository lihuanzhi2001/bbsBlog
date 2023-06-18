package com.easybbs.service;

import com.easybbs.entity.po.ComplaintRecord;
import com.easybbs.entity.query.ComplaintRecordQuery;
import com.easybbs.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/3/30 20:02
 */
public interface ComplaintRecordService {
    /**
     * 根据条件查询列表
     */
    List<ComplaintRecord> findListByParam(ComplaintRecordQuery param);

    /**
     * 根据条件查询总数
     */
    Integer findCountByParam(ComplaintRecordQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<ComplaintRecord> findListByPage(ComplaintRecordQuery param);

    /**
     * 新增
     */
    Integer add(ComplaintRecord bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<ComplaintRecord> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<ComplaintRecord> listBean);


    /**
     * 根据id查询对象
     */
    ComplaintRecord getComplaintRecordById(Integer id);


    /**
     * 根据id修改
     */
    Integer updateComplaintRecordById(ComplaintRecord bean, Integer id);


    /**
     * 根据id删除
     */
    Integer deleteComplaintRecordById(Integer id);


    /**
     * 根据id更新投诉记录的状态status(0 - 未处理，1 - 处理中，2 - 已处理，3 - 已关闭)
     */
    Integer updateComplaintStatusById(Integer id, Integer status);
}
