package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.constants.Constants;
import com.easybbs.entity.dto.FileUploadDto;
import com.easybbs.entity.dto.SessionWebUserDto;
import com.easybbs.entity.enums.FileUploadTypeEnum;
import com.easybbs.entity.enums.ImgGalleryStatusEnum;
import com.easybbs.entity.enums.UserPermissionEnum;
import com.easybbs.entity.po.ImgCategory;
import com.easybbs.entity.po.ImgGallery;
import com.easybbs.entity.po.UserPermission;
import com.easybbs.entity.query.ImgCategoryQuery;
import com.easybbs.entity.query.ImgGalleryQuery;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.entity.vo.web.ImgGalleryVo;
import com.easybbs.exception.BusinessException;
import com.easybbs.service.ImgCategoryService;
import com.easybbs.service.ImgGalleryService;
import com.easybbs.service.UserPermissionService;
import com.easybbs.utils.FileUtils;
import com.easybbs.utils.JsonUtils;
import com.easybbs.utils.SysCacheUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lihuanzhi
 * @since 2023/4/24 13:44
 */
@RestController
@RequestMapping("/imgGallery")
public class ImgGalleryController extends BaseController {

    @Resource
    private ImgGalleryService imgGalleryService;

    @Resource
    ImgCategoryService imgCategoryService;

    @Resource
    private FileUtils fileUtils;

    @Resource
    private UserPermissionService userPermissionService;

    /**
     * 加载所有分类
     *
     * @return
     */
    @RequestMapping("/loadCategory")
    public ResponseVO loadCategory() {
        ImgCategoryQuery query = new ImgCategoryQuery();
        query.setOrderBy("`order`");
        List<ImgCategory> imgCategories = imgCategoryService.findListByParam(query);
//        // 封装
//        Map<Integer, String> collect = imgCategories.stream()
//                .collect(Collectors.toMap(ImgCategory::getId, ImgCategory::getName));
//

        return getSuccessResponseVO(imgCategories);
    }

    /**
     * 图库首页加载
     *
     * @return
     */
    @RequestMapping("/load")
    public ResponseVO loadImgs(Integer categoryId) {
        ImgGalleryQuery query = new ImgGalleryQuery();
        if (categoryId != null && categoryId != 0)
            query.setCategoryId(categoryId);
        query.setStatus(ImgGalleryStatusEnum.AUDIT.getCode());
        query.setStatus3(ImgGalleryStatusEnum.UN_FREEZE.getCode());
        query.setOrderBy("update_time desc");

//        PaginationResultVO<ImgGallery> paginationResultVO =
//                imgGalleryService.findListByPage(query);
        imgGalleryService.findListByParam(query);
//        List<ImgGallery> list = paginationResultVO.getList();
        List<ImgGallery> list = imgGalleryService.findListByParam(query);
        List<ImgGallery> collect = list.stream()
                .map(imgGallery -> {
                    if (ImgGalleryStatusEnum.INFO_HIDE.getCode()
                            .equals(imgGallery.getStatus2())) {
                        imgGallery.setUserId(null);
                        imgGallery.setNickName(null);
                    }
                    return imgGallery;
                })
                .collect(Collectors.toList());


        return getSuccessResponseVO(collect);
    }

    /**
     * 查询用户中心的图库(分status, 分类categoryId)
     */
    @RequestMapping("/loadCenterImgs")
    @GlobalInterceptor(checkParams = true, checkLogin = true)
    public ResponseVO loadCenterImgs(HttpSession session,
                                     Integer status,
                                     Integer categoryId,
                                     Integer type,
                                     Integer pageNo,
                                     Integer pageSize) {
        // 0 - (查询 未审核 已审核 审核未通过) 1 - (查询已冻结)

        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (userDto == null) {
            throw new BusinessException("请先登录");
        }
        if (categoryId != null) {
            ImgCategory ic = imgCategoryService.getImgCategoryById(categoryId);
            if (ic == null) {
                throw new BusinessException("图片分类错误！");
            }
        }


        ImgGalleryQuery query = new ImgGalleryQuery();
        query.setCategoryId(categoryId);
        query.setPageNo(pageNo);
        query.setPageSize(pageSize);

        if (type == 0) {
            query.setStatus(status);
            query.setStatus3(ImgGalleryStatusEnum.UN_FREEZE.getCode());
        } else if (type == 1) {
            query.setStatus3(ImgGalleryStatusEnum.FREEZE.getCode());
        } else {
            throw new BusinessException("查询类型参数异常");
        }

        query.setUserId(userDto.getUserId());
        query.setOrderBy("update_time desc");

        PaginationResultVO<ImgGalleryVo> imgGalleryVoPaginationResultVO
                = convert2PaginationVO(imgGalleryService.findListByPage(query),
                ImgGalleryVo.class);


//        List<ImgGalleryVo> imgGalleryVos = CopyTools.copyList(imgGalleryService.findListByParam(query), ImgGalleryVo.class);
        imgGalleryVoPaginationResultVO.getList().stream()
                .forEach(vo -> vo.setShow(vo.getStatus2() == 1));
        return getSuccessResponseVO(imgGalleryVoPaginationResultVO);
    }

    /**
     * 上传图片n
     */
    @RequestMapping("/add")
    @GlobalInterceptor(checkParams = true, checkLogin = true)
    public ResponseVO addImg(HttpSession session,
                             MultipartFile img,
                             Integer categoryId,
                             Integer status2) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (userDto == null) {
            throw new BusinessException("请先登录");
        }
        if (img == null) {
            throw new BusinessException("请选择上传图片");
        }
        ImgCategory ic = imgCategoryService.getImgCategoryById(categoryId);
        if (ic == null) {
            throw new BusinessException("图片分类错误！");
        }
        if (status2 == null) {
            status2 = ImgGalleryStatusEnum.INFO_HIDE.getCode();
        }

        // 上传图片需要权限校验
        checkPermission(userDto.getUserId(), UserPermissionEnum.POST_IMG);

        //保存图片
        FileUploadDto uploadDto = fileUtils.uploadFile2Local(img,
                FileUploadTypeEnum.IMG_GALLERY,
                Constants.FILE_FOLDER_IMAGE);

        // 查询是否需要审核
        Boolean imgNeedAudit = SysCacheUtils.getSysSetting().getAuditStting().getImgAudit();

        ImgGallery bean = new ImgGallery();
        bean.setUserId(userDto.getUserId());
        bean.setNickName(userDto.getNickName());
        bean.setCategoryId(ic.getId());
        bean.setCategoryName(ic.getName());
        bean.setImg(uploadDto.getLocalPath());
        // 设置是否已审核状态
        if (imgNeedAudit) {
            bean.setStatus(ImgGalleryStatusEnum.NO_AUDIT.getCode());
        } else {
            bean.setStatus(ImgGalleryStatusEnum.AUDIT.getCode());
        }
        bean.setStatus2(status2);
        bean.setStatus3(ImgGalleryStatusEnum.UN_FREEZE.getCode());
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());

        imgGalleryService.add(bean);

        return getSuccessResponseVO(null);
    }

    /**
     * 根据id修改用户信息状态
     */
    @RequestMapping("/changeStatus2")
    @GlobalInterceptor(checkParams = true, checkLogin = true)
    public ResponseVO changeStatus2(HttpSession session, Integer id, Integer status2) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (userDto == null) {
            throw new BusinessException("请先登录");
        }
//        boolean b1 = ImgGalleryStatusEnum.INFO_HIDE.getCode() == status2;
//        boolean b2 = ImgGalleryStatusEnum.INFO_SHOW.getCode() == status2;
//        if (ImgGalleryStatusEnum.INFO_HIDE.getCode() != status2 && ImgGalleryStatusEnum.INFO_HIDE.getCode() != status2){
//            throw new BusinessException("要修改的状态异常");
//        }

        ImgGallery imgGalleryById = imgGalleryService.getImgGalleryById(id);
        if (imgGalleryById == null) {
            throw new BusinessException("要修改的图片不存在");
        }
        if (!imgGalleryById.getUserId().equals(userDto.getUserId())) {
            throw new BusinessException("不是你的图片，无法修改");
        }
        imgGalleryService.updateImgGalleryStatus2ById(id, status2);

        return getSuccessResponseVO(null);

    }

    /**
     * 根据id删除
     */
    @RequestMapping("/delete")
    @GlobalInterceptor(checkParams = true, checkLogin = true)
    public ResponseVO deleteImg(HttpSession session, Integer id) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (userDto == null) {
            throw new BusinessException("请先登录");
        }
        ImgGallery imgGalleryById = imgGalleryService.getImgGalleryById(id);
        if (imgGalleryById == null) {
            throw new BusinessException("要删除的图片不存在");
        }
        if (!imgGalleryById.getUserId().equals(userDto.getUserId())) {
            throw new BusinessException("不是你的图片，无法删除");
        }

        imgGalleryService.deleteImgGalleryById(id);

        return getSuccessResponseVO(null);
    }

    /**
     * 根据id冻结图片
     */
    @RequestMapping("/freeze")
    @GlobalInterceptor(checkParams = true, checkLogin = true)
    public ResponseVO freezeImg(HttpSession session, Integer id) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (userDto == null) {
            throw new BusinessException("请先登录");
        }
        ImgGallery imgGalleryById = imgGalleryService.getImgGalleryById(id);
        if (imgGalleryById == null) {
            throw new BusinessException("要冻结的图片不存在");
        }
        if (!imgGalleryById.getUserId().equals(userDto.getUserId())) {
            throw new BusinessException("不是你的图片，无法冻结");
        }
        if (ImgGalleryStatusEnum.ERROR_AUDIT.getCode().equals(imgGalleryById.getStatus())) {
            throw new BusinessException("改图片未审核通过，无法操作!");
        }

        imgGalleryService.updateImgGalleryStatus3ById(id, ImgGalleryStatusEnum.FREEZE.getCode());

        return getSuccessResponseVO(null);
    }

    /**
     * 根据id解冻图片
     *
     * @param session
     * @param id
     * @return
     */
    @RequestMapping("/unFreeze")
    @GlobalInterceptor(checkParams = true, checkLogin = true)
    public ResponseVO unFreezeImg(HttpSession session, Integer id) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (userDto == null) {
            throw new BusinessException("请先登录");
        }
        ImgGallery imgGalleryById = imgGalleryService.getImgGalleryById(id);
        if (imgGalleryById == null) {
            throw new BusinessException("要冻结的图片不存在");
        }
        if (!imgGalleryById.getUserId().equals(userDto.getUserId())) {
            throw new BusinessException("不是你的图片，无法冻结");
        }
        if (imgGalleryById.getStatus().equals(ImgGalleryStatusEnum.ERROR_AUDIT.getCode())) {
            throw new BusinessException("改图片未审核通过，无法操作!");
        }

        // 解冻图片需要权限校验
        checkPermission(userDto.getUserId(), UserPermissionEnum.UN_FREEZE_IMG);

        imgGalleryService.updateImgGalleryStatus3ById(id, ImgGalleryStatusEnum.UN_FREEZE.getCode());

        return getSuccessResponseVO(null);
    }

    /**
     * 权限校验方法...
     *
     * @param userId
     * @param enumItem
     */
    private void checkPermission(String userId, UserPermissionEnum enumItem) {
        UserPermission userPermission = userPermissionService.getUserPermissionByUserId(userId);
        List<String> permissions = JsonUtils.convertJsonArray2List(userPermission.getJsonPermission(), String.class);
        boolean flag = true;
        for (String permission : permissions) {
            if (permission.equals(enumItem.getCode())) {
                flag = false;
                continue;
            }
        }
        if (flag) {
            throw new BusinessException("不好意思，您没有【" + enumItem.getDesc() + "】的权限！");
        }
    }

}
