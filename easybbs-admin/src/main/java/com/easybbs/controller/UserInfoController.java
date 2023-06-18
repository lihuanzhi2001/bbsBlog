package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.dto.PermissionDto;
import com.easybbs.entity.dto.PermissionItemDto;
import com.easybbs.entity.enums.UserPermissionEnum;
import com.easybbs.entity.po.UserPermission;
import com.easybbs.entity.query.UserInfoQuery;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.service.UserInfoService;
import com.easybbs.service.UserPermissionService;
import com.easybbs.utils.JsonUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserInfoController extends BaseController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserPermissionService userPermissionService;

    @RequestMapping("/loadUserList")
    @GlobalInterceptor
    public ResponseVO loadUserList(UserInfoQuery userInfoQuery) {
        userInfoQuery.setOrderBy("join_time desc");
        return getSuccessResponseVO(userInfoService.findListByPage(userInfoQuery));
    }


    @RequestMapping("/updateUserStatus")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO updateUserStatus(@VerifyParam(required = true) Integer status, @VerifyParam(required = true) String userId) {
        // 修改用户状态
        userInfoService.updateUserStatus(status, userId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/sendMessage")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO sendMessage(@VerifyParam(required = true) String userId,
                                  @VerifyParam(required = true) String message,
                                  Integer integral) {
        userInfoService.sendMessage(userId, message, integral);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadPermission")
    public ResponseVO loadPermission(@VerifyParam(required = true) String userId) {
        UserPermission userPermission = userPermissionService.getUserPermissionByUserId(userId);
        String jsonPermission = userPermission.getJsonPermission();
//        UserPermissionDto userPermissionDto = JsonUtils.convertJson2Obj(jsonPermission, UserPermissionDto.class);
//        UserPermissionDto userPermissionDto = JsonUtils.convertJson2Obj(jsonPermission, UserPermissionDto.class);
        // 封装一下这个数据，这样前端就好展示了

        // 所有权限
//        Map<String, String> allPerm = new HashMap<>();
        List<PermissionItemDto> allPerm = new ArrayList<>();
        for (UserPermissionEnum enumIte : UserPermissionEnum.getAllItem()) {
            PermissionItemDto item = new PermissionItemDto();
            item.setName(enumIte.getDesc());
            item.setValue(enumIte.getCode());
            allPerm.add(item);
        }

        // 用户拥有的权限
//        List<PermissionItemDto> havePerm = new ArrayList<>();
//        List<String> havePermList = JsonUtils.convertJsonArray2List(jsonPermission, String.class);
//        for (String s : havePermList) {
//            UserPermissionEnum itemEnum = UserPermissionEnum.getByCode(s);
//            PermissionItemDto item = new PermissionItemDto();
//            item.setName(itemEnum.getDesc());
//            item.setValue(itemEnum.getCode());
//            havePerm.add(item);
//        }

        // 用户拥有的权限就不用封装了
        List<String> havePerm = JsonUtils.convertJsonArray2List(jsonPermission, String.class);


        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setAllPerm(allPerm);
        permissionDto.setHavePerm(havePerm);

        return getSuccessResponseVO(permissionDto);
    }

    /**
     * 设置更新用户权限
     *
     * @param userId      用户id
     * @param permissions 权限json串
     * @return
     */
    @RequestMapping("/permissionSetting")
    public ResponseVO permissionSetting(
            @VerifyParam(required = true) String userId,
            @VerifyParam(required = true) String permissions) {

        List<String> havePerm = Str2Array(permissions);

        UserPermission userPermission = new UserPermission();
        userPermission.setUserId(userId);
        userPermission.setJsonPermission(JsonUtils.convertObj2Json(havePerm));
        // 更新用户权限json串
        userPermissionService.updateUserPermissionByUserId(userPermission, userId);

        return getSuccessResponseVO(null);

    }

    // 子服串转数组
    private List<String> Str2Array(String permissions) {
        List<String> array = new ArrayList<>();
        if (permissions == null || permissions.trim().equals("")) {
            return array;
        }
        String[] split = permissions.split(",");
        for (String s : split) {
            array.add(s);
        }

        return array;
    }

}
