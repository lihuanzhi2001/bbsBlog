package com.easybbs.service;


import com.easybbs.entity.po.Notice;
import com.easybbs.entity.query.NoticeQuery;
import com.easybbs.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/4 17:38
 */
public interface NoticeService {
    /**
     * 根据条件查询列表
     */
    List<Notice> findListByParam(NoticeQuery param);

    /**
     * 根据条件查询总数
     */
    Integer findCountByParam(NoticeQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<Notice> findListByPage(NoticeQuery param);

    /**
     * 新增
     */
    Integer add(Notice bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<Notice> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<Notice> listBean);

    /**
     * 根据id查询对象
     */
    Notice getNoticeById(Integer id);


    /**
     * 根据id修改
     */
    Integer updateNoticeById(Notice bean, Integer id);


    /**
     * 根据id删除
     */
    Integer deleteNoticeById(Integer id);


    /**
     * 根据id更新投诉记录的状态status(0 - 未发布，1 - 已发布)
     */
    Integer updateNoticeStatusById(Integer id, Integer status);
}
