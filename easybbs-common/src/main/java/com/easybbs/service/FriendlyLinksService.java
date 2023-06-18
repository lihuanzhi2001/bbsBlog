package com.easybbs.service;

import com.easybbs.entity.po.FriendlyLinks;
import com.easybbs.entity.query.FriendlyLinksQuery;
import com.easybbs.entity.vo.PaginationResultVO;

import java.util.List;

public interface FriendlyLinksService {
    /**
     * 根据条件查询列表
     */
    List<FriendlyLinks> findListByParam(FriendlyLinksQuery param);

    /**
     * 根据条件查询列表
     */
    Integer findCountByParam(FriendlyLinksQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<FriendlyLinks> findListByPage(FriendlyLinksQuery param);

    /**
     * 新增
     */
    Integer add(FriendlyLinks bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<FriendlyLinks> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<FriendlyLinks> listBean);

    /**
     * 根据id查询对象
     */
    FriendlyLinks getFriendlyLinksById(Integer id);


    /**
     * 根据id修改
     */
    Integer updateFriendlyLinksById(FriendlyLinks bean, Integer id);


    /**
     * 根据id删除
     */
    Integer deleteFriendlyLinksById(Integer id);


    /**
     * 根据id更新投诉记录的状态status(0 - 未发布，1 - 已发布)
     */
    Integer updateFriendlyLinksStatusById(Integer id, Integer status);
}
