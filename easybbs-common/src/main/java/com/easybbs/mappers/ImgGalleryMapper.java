package com.easybbs.mappers;

import com.easybbs.entity.po.ImgGallery;
import org.apache.ibatis.annotations.Param;

/**
 * @author lihuanzhi
 * @since 2023/4/24 10:29
 */
public interface ImgGalleryMapper<T, P> extends BaseMapper<T, P> {
    /**
     * 根据id查询对象
     */
    ImgGallery getImgGalleryById(@Param("id") Integer id);


    /**
     * 根据id修改
     */
    Integer updateImgGalleryById(@Param("bean") T t, @Param("id") Integer id);


    /**
     * 根据id删除
     */
    Integer deleteImgGalleryById(@Param("id") Integer id);

    /**
     * 根据id修改分类
     */
    Integer updateImgGalleryCategoryById(@Param("id") Integer id, @Param("categoryId") Integer cid, @Param("categoryName") Integer cname);

    /**
     * 根据id更新状态status(0 - 未审核, 1 - 已审核)
     */
    Integer updateImgGalleryStatusById(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 根据id更新信息状态status2(0 - 隐藏, 1 - 展示)
     */
    Integer updateImgGalleryStatus2ById(@Param("id") Integer id, @Param("status2") Integer status2);

    /**
     * 根据id更新信息状态status3(0 - 未冻结, 1 - 已冻结)
     */
    Integer updateImgGalleryStatus3ById(@Param("id") Integer id, @Param("status3") Integer status3);
}
