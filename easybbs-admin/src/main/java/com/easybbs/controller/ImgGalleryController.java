package com.easybbs.controller;

import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.enums.ImgGalleryStatusEnum;
import com.easybbs.entity.po.ImgCategory;
import com.easybbs.entity.query.ImgCategoryQuery;
import com.easybbs.entity.query.ImgGalleryQuery;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.exception.BusinessException;
import com.easybbs.service.ImgCategoryService;
import com.easybbs.service.ImgGalleryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/25 13:52
 */
@RestController
@RequestMapping("/imgGallery")
public class ImgGalleryController extends BaseController {


    @Resource
    private ImgGalleryService imgGalleryService;

    @Resource
    private ImgCategoryService imgCategoryService;

    // 加载所有分类
    @RequestMapping("/loadCategory")
    public ResponseVO loadCategory() {
        ImgCategoryQuery query = new ImgCategoryQuery();
        query.setOrderBy("`order`");
        List<ImgCategory> imgCategories = imgCategoryService.findListByParam(query);

        return getSuccessResponseVO(imgCategories);
    }

    /**
     * 按条件查询
     *
     * @return
     */
    @RequestMapping("/load")
    public ResponseVO load(ImgGalleryQuery query) {
        query.setOrderBy("update_time desc");
        return getSuccessResponseVO(imgGalleryService.findListByPage(query));
    }

    /**
     * 批量审核操作
     *
     * @param ids
     * @param type
     * @return
     */
    @RequestMapping("/audit")
    public ResponseVO audit(String ids, Integer type) {
        String[] split = ids.split(",");
        if (ImgGalleryStatusEnum.NO_AUDIT.getCode() != type
                && ImgGalleryStatusEnum.AUDIT.getCode() != type
                && ImgGalleryStatusEnum.ERROR_AUDIT.getCode() != type) {
            throw new BusinessException("要修改的状态错误!");
        }
        for (String id : split) {
            imgGalleryService.updateImgGalleryStatusById(Integer.parseInt(id), type);
        }

        return getSuccessResponseVO(null);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public ResponseVO delete(String ids) {
        String[] split = ids.split(",");
        for (String id : split) {
            imgGalleryService.deleteImgGalleryById(Integer.parseInt(id));
        }

        return getSuccessResponseVO(null);
    }


}
