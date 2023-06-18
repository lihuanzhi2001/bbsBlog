package com.easybbs.mappers;

import com.easybbs.entity.po.ImgCategory;
import org.apache.ibatis.annotations.Param;

/**
 * @author lihuanzhi
 * @since 2023/4/24 10:26
 */
public interface ImgCategoryMapper<T, P> extends BaseMapper<T, P> {
    /**
     * 根据id查询对象
     */
    ImgCategory getImgCategoryById(@Param("id") Integer id);


    /**
     * 根据id修改
     */
    Integer updateImgCategoryById(@Param("bean") T t, @Param("id") Integer id);


    /**
     * 根据id删除
     */
    Integer deleteImgCategoryById(@Param("id") Integer id);
}
