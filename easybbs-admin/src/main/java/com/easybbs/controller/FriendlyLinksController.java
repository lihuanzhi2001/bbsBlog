package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.constants.Constants;
import com.easybbs.entity.dto.FileUploadDto;
import com.easybbs.entity.enums.FileUploadTypeEnum;
import com.easybbs.entity.po.FriendlyLinks;
import com.easybbs.entity.query.FriendlyLinksQuery;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.exception.BusinessException;
import com.easybbs.service.FriendlyLinksService;
import com.easybbs.utils.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/friendlyLinks")
public class FriendlyLinksController extends BaseController {

    @Resource
    private FriendlyLinksService friendlyLinksService;

    @Resource
    private FileUtils fileUtils;

    // 查询
    @RequestMapping("/loadLinks")
    public ResponseVO loadLinks(FriendlyLinksQuery query) {
        query.setOrderBy("`order`");
        return getSuccessResponseVO(friendlyLinksService.findListByPage(query));

    }

    // 新增
    @RequestMapping("/add")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO addLinks(@VerifyParam(required = true) String title,
                               @VerifyParam(required = true) String desc,
                               @VerifyParam(required = true) String address,
                               Integer order,
                               MultipartFile icon) {

        FriendlyLinks bean = new FriendlyLinks();
        bean.setTitle(title);
        bean.setDesc(desc);
        bean.setAddress(address);
        if (order == null || order == 0) {
            order = 1;
        }
        bean.setOrder(order);
        bean.setStatus(0);
        if (icon != null) {
            FileUploadDto uploadDto = fileUtils.uploadFile2Local(icon, FileUploadTypeEnum.LINK_ICON, Constants.FILE_FOLDER_IMAGE);
            bean.setIcon(uploadDto.getLocalPath());
        }

        friendlyLinksService.add(bean);
        return getSuccessResponseVO(null);
    }

    // 修改
    @RequestMapping("/edit")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO editFriendlyLinks(@VerifyParam(required = true) Integer id,
                                        @VerifyParam(required = true) String title,
                                        @VerifyParam(required = true) String desc,
                                        @VerifyParam(required = true) String address,
                                        Integer order,
                                        MultipartFile icon) {

        // 如果该连接为已发布的状态则要求撤销之后再执行修改操作
        FriendlyLinks friendlyLinks = friendlyLinksService.getFriendlyLinksById(id);
        if (friendlyLinks.getStatus() == 1) {
            throw new BusinessException("当前链接为发布状态，请撤回后再操作!");
        }
        FriendlyLinks bean = new FriendlyLinks();
        bean.setTitle(title);
        bean.setDesc(desc);
        bean.setAddress(address);
        if (order == null || order == 0) {
            order = 1;
        }
        bean.setOrder(order);
        // 有icon就更新，否则不更新
        if (icon != null) {
            FileUploadDto uploadDto = fileUtils.uploadFile2Local(icon, FileUploadTypeEnum.LINK_ICON, Constants.FILE_FOLDER_IMAGE);
            bean.setIcon(uploadDto.getLocalPath());
        }

        friendlyLinksService.updateFriendlyLinksById(bean, id);
        return getSuccessResponseVO(null);
    }

    // 批量切换状态
    @RequestMapping("/changeStatus")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO changeStatus(@VerifyParam(required = true) String ids,
                                   @VerifyParam(required = true) Integer status) {

        String[] idArray = ids.split(",");
        for (String id : idArray) {
            friendlyLinksService.updateFriendlyLinksStatusById(Integer.parseInt(id), status);
        }

        return getSuccessResponseVO(null);
    }

    // 批量删除
    @RequestMapping("/del")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO delFriendlyLinks(@VerifyParam(required = true) String ids) {

        String[] idArray = ids.split(",");
        for (String id : idArray) {
            friendlyLinksService.deleteFriendlyLinksById(Integer.parseInt(id));
        }

        return getSuccessResponseVO(null);
    }


}
