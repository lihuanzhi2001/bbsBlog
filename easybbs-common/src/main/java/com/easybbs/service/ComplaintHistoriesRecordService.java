package com.easybbs.service;

import com.easybbs.entity.po.ComplaintHistoriesRecord;
import com.easybbs.entity.query.ComplaintHistoriesRecordQuery;
import com.easybbs.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/3/30 20:02
 */
public interface ComplaintHistoriesRecordService {
    /**
     * 根据条件查询列表
     */
    List<ComplaintHistoriesRecord> findListByParam(ComplaintHistoriesRecordQuery param);

    /**
     * 根据条件查询总数
     */
    Integer findCountByParam(ComplaintHistoriesRecordQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<ComplaintHistoriesRecord> findListByPage(ComplaintHistoriesRecordQuery param);

    /**
     * 新增
     */
    Integer add(ComplaintHistoriesRecord bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<ComplaintHistoriesRecord> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<ComplaintHistoriesRecord> listBean);


    /**
     * 根据id查询对象
     */
    ComplaintHistoriesRecord getComplaintHistoriesById(Integer id);


    /**
     * 根据id修改
     */
    Integer updateComplaintHistoriesById(ComplaintHistoriesRecord bean, Integer id);


    /**
     * 根据id删除
     */
    Integer deleteComplaintHistoriesById(Integer id);


}
