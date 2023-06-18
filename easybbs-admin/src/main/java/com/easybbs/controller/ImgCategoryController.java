package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.po.ImgCategory;
import com.easybbs.entity.query.ImgCategoryQuery;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.exception.BusinessException;
import com.easybbs.service.ImgCategoryService;
import com.easybbs.service.ImgGalleryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lihuanzhi
 * @since 2023/4/25 13:54
 */
@RestController
@RequestMapping("/imgCategory")
public class ImgCategoryController extends BaseController {
    @Resource
    private ImgCategoryService imgCategoryService;

    @Resource
    private ImgGalleryService imgGalleryService;

    @RequestMapping("/load")
    public ResponseVO load(ImgCategoryQuery query) {
        query.setOrderBy("`order`");
        return getSuccessResponseVO(imgCategoryService.findListByPage(query));
    }

    // 新增
    @RequestMapping("add")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO add(@VerifyParam(required = true) String name,
                          Integer order) {
        if (order == null || order == 0) {
            order = 1;
        }
        ImgCategory bean = new ImgCategory();
        bean.setOrder(order);
        bean.setName(name);
        imgCategoryService.add(bean);

        return getSuccessResponseVO(null);
    }

    // 修改
    @RequestMapping("edit")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO edit(@VerifyParam(required = true) Integer id,
                           @VerifyParam(required = true) String name,
                           Integer order) {
        if (order == null || order == 0) {
            order = 1;
        }
        ImgCategory imgCategoryById = imgCategoryService.getImgCategoryById(id);
        if (imgCategoryById == null) {
            throw new BusinessException("要修改的分类不存在。。.");
        }
        ImgCategory bean = new ImgCategory();
        bean.setOrder(order);
        bean.setName(name);
        imgCategoryService.updateImgCategoryById(bean, id);

        return getSuccessResponseVO(null);
    }

    // 删除
    @RequestMapping("delete")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO delete(@VerifyParam(required = true) String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            imgCategoryService.deleteImgCategoryById(Integer.parseInt(s));
        }

        return getSuccessResponseVO(null);
    }
}
