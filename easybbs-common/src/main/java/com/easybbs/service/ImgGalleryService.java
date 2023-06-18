package com.easybbs.service;

import com.easybbs.entity.po.ImgGallery;
import com.easybbs.entity.query.ImgGalleryQuery;
import com.easybbs.entity.vo.PaginationResultVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/24 11:41
 */
public interface ImgGalleryService {
    /**
     * 根据条件查询列表
     */
    List<ImgGallery> findListByParam(ImgGalleryQuery param);

    /**
     * 根据条件查询列表
     */
    Integer findCountByParam(ImgGalleryQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<ImgGallery> findListByPage(ImgGalleryQuery param);

    /**
     * 新增
     */
    Integer add(ImgGallery bean);


    /**
     * 根据id查询对象
     */
    ImgGallery getImgGalleryById(Integer id);


    /**
     * 根据id修改
     */
    Integer updateImgGalleryById(ImgGallery bean, @Param("id") Integer id);

    /**
     * 根据id删除
     */
    Integer deleteImgGalleryById(Integer id);

    /**
     * 根据id修改分类
     */
    Integer updateImgGalleryCategoryById(Integer id, Integer cid, Integer cname);

    /**
     * 根据id更新状态status(0 - 未审核, 1 - 已审核, 2 - 已冻结)
     */
    Integer updateImgGalleryStatusById(Integer id, Integer status);

    /**
     * 根据id更新信息状态status2(0 - 隐藏, 1 - 展示)
     */
    Integer updateImgGalleryStatus2ById(Integer id, Integer status2);


    /**
     * 根据id更新冻结状态status2(0 - 未冻结, 1 - 已冻结)
     */
    Integer updateImgGalleryStatus3ById(Integer id, Integer status3);
}
