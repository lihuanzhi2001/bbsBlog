package com.easybbs.service;

import com.easybbs.entity.po.ImgCategory;
import com.easybbs.entity.query.ImgCategoryQuery;
import com.easybbs.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/24 11:41
 */
public interface ImgCategoryService {
    /**
     * 根据条件查询列表
     */
    List<ImgCategory> findListByParam(ImgCategoryQuery param);

    /**
     * 根据条件查询列表
     */
    Integer findCountByParam(ImgCategoryQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<ImgCategory> findListByPage(ImgCategoryQuery param);

    /**
     * 新增
     */
    Integer add(ImgCategory bean);

    /**
     * 插入或者更新
     *
     * @param bean
     * @return
     */
    Integer insertOrUpdate(ImgCategory bean);

    /**
     * 根据id查询对象
     */
    ImgCategory getImgCategoryById(Integer id);


    /**
     * 根据id修改
     */
    Integer updateImgCategoryById(ImgCategory bean, Integer id);


    /**
     * 根据id删除
     */
    Integer deleteImgCategoryById(Integer id);
}
