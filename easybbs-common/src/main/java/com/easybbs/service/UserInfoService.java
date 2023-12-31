package com.easybbs.service;

import com.easybbs.entity.dto.SessionWebUserDto;
import com.easybbs.entity.enums.UserIntegralOperTypeEnum;
import com.easybbs.entity.po.UserInfo;
import com.easybbs.entity.query.UserInfoQuery;
import com.easybbs.entity.vo.PaginationResultVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 用户信息 业务接口
 */
public interface UserInfoService {

    /**
     * 根据条件查询列表
     */
    List<UserInfo> findListByParam(UserInfoQuery param);

    /**
     * 根据条件查询列表
     */
    Integer findCountByParam(UserInfoQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<UserInfo> findListByPage(UserInfoQuery param);

    /**
     * 新增
     */
    Integer add(UserInfo bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<UserInfo> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<UserInfo> listBean);

    /**
     * 根据UserId查询对象
     */
    UserInfo getUserInfoByUserId(String userId);


    /**
     * 根据UserId修改
     */
    Integer updateUserInfoByUserId(UserInfo bean, String userId);


    /**
     * 根据UserId删除
     */
    Integer deleteUserInfoByUserId(String userId);

    /**
     * 根据UserName查询对象
     */
    UserInfo getUserInfoByUsername(String username);


    /**
     * 根据UserName修改
     */
    Integer updateUserInfoByUsername(UserInfo bean, String username);


    /**
     * 根据UserName删除
     */
    Integer deleteUserInfoByUsername(String username);


    /**
     * 根据Email查询对象
     */
    UserInfo getUserInfoByEmail(String email);


    /**
     * 根据Email修改
     */
    Integer updateUserInfoByEmail(UserInfo bean, String email);


    /**
     * 根据Email删除
     */
    Integer deleteUserInfoByEmail(String email);


    /**
     * 根据NickName查询对象
     */
    UserInfo getUserInfoByNickName(String nickName);


    /**
     * 根据NickName修改
     */
    Integer updateUserInfoByNickName(UserInfo bean, String nickName);


    /**
     * 根据NickName删除
     */
    Integer deleteUserInfoByNickName(String nickName);

    SessionWebUserDto login(String username, String password, String ip);

//    void register(String email, String nickName, String password, String emailCode);
    // TODO: old register
//    void register(String email, String nickName, String password);

    // TODO: new register
    void register(String username, String nickName, String password);

    void resetPwdByEmail(String email, String password, String emailCode);

    void resetPwdByOldPwd(String username, String oldPassword, String newPassword);

//    void resetPwd(String email, String password, String emailCode);

    void updateUserIntegral(String userId, UserIntegralOperTypeEnum operTypeEnum, Integer changeType, Integer integral);

    void updateUserInfo(UserInfo userInfo, MultipartFile avatar);

    void updateUserStatus(Integer status, String userId);

    void sendMessage(String userId, String message, Integer integral);

    void bindEmail(String userId, String email, String emailCode, Integer type);
}