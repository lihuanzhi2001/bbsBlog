package com.easybbs.controller;

import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.query.FriendlyLinksQuery;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.service.FriendlyLinksService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lihuanzhi
 * @since 2023/4/23 14:25
 */
@RestController
@RequestMapping("/friendlyLinks")
public class FriendlyLinksController extends BaseController {

    @Resource
    private FriendlyLinksService friendlyLinksService;

    /**
     * 查询所有已发布的友链
     */
    @RequestMapping("loadLinks")
    public ResponseVO loadLinks() {
        FriendlyLinksQuery query = new FriendlyLinksQuery();
        // 0: 已发布，1: 未发布
        query.setStatus(1);
        query.setOrderBy("`order`");
        return getSuccessResponseVO(this
                .friendlyLinksService
                .findListByParam(query));
    }
}
