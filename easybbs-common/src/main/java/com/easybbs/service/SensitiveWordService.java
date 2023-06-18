package com.easybbs.service;

import com.easybbs.entity.po.SensitiveWord;
import com.easybbs.entity.query.SensitiveWordQuery;
import com.easybbs.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/11 9:32
 */
public interface SensitiveWordService {
    /**
     * 根据条件查询列表
     */
    List<SensitiveWord> findListByParam(SensitiveWordQuery param);

    /**
     * 根据条件查询总数
     */
    Integer findCountByParam(SensitiveWordQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<SensitiveWord> findListByPage(SensitiveWordQuery param);

    /**
     * 新增
     */
    Integer add(SensitiveWord bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<SensitiveWord> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<SensitiveWord> listBean);

    /**
     * 根据id查询对象
     */
    SensitiveWord getSensitiveWordById(Integer id);


    /**
     * 根据id修改
     */
    Integer updateSensitiveWordById(SensitiveWord bean, Integer id);


    /**
     * 根据id删除
     */
    Integer deleteSensitiveWordById(Integer id);


    /**
     * 根据id更新敏感词的状态status(0 - 未使用，1 - 使用)
     */
    Integer updateSensitiveWordStatusById(Integer id, Integer status);
}
