package com.easybbs.service.impl;

import com.easybbs.entity.config.WebConfig;
import com.easybbs.entity.constants.Constants;
import com.easybbs.entity.dto.SessionWebUserDto;
import com.easybbs.entity.dto.UserPrivacyDto;
import com.easybbs.entity.enums.*;
import com.easybbs.entity.po.*;
import com.easybbs.entity.query.*;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.exception.BusinessException;
import com.easybbs.mappers.ForumArticleMapper;
import com.easybbs.mappers.ForumCommentMapper;
import com.easybbs.mappers.UserInfoMapper;
import com.easybbs.mappers.UserIntegralRecordMapper;
import com.easybbs.service.*;
import com.easybbs.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;


/**
 * 用户信息 业务接口实现
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

    @Resource
    private WebConfig webConfig;

    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

    @Resource
    private UserIntegralRecordMapper<UserIntegralRecord, UserIntegralRecordQuery> userIntegralRecordMapper;

    @Resource
    private FileUtils fileUtils;

    @Resource
    private EmailCodeService emailCodeService;

    @Resource
    private UserMessageService userMessageService;

    @Resource
    private ForumArticleMapper<ForumArticle, ForumArticleQuery> forumArticleMapper;

    @Resource
    private ForumCommentMapper<ForumComment, ForumCommentQuery> forumCommentMapper;

    @Resource
    private UserPrivacyService userPrivacyService;

    @Resource
    private UserPermissionService userPermissionService;

    /**
     * 根据条件查询列表
     */
    @Override

    public List<UserInfo> findListByParam(UserInfoQuery param) {
        return this.userInfoMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(UserInfoQuery param) {
        return this.userInfoMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<UserInfo> findListByPage(UserInfoQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<UserInfo> list = this.findListByParam(param);
        PaginationResultVO<UserInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(UserInfo bean) {
        return this.userInfoMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据UserId获取对象
     */
    @Override
    public UserInfo getUserInfoByUserId(String userId) {
        return this.userInfoMapper.selectByUserId(userId);
    }

    /**
     * 根据UserId修改
     */
    @Override
    public Integer updateUserInfoByUserId(UserInfo bean, String userId) {
        return this.userInfoMapper.updateByUserId(bean, userId);
    }

    /**
     * 根据UserId删除
     */
    @Override
    public Integer deleteUserInfoByUserId(String userId) {
        return this.userInfoMapper.deleteByUserId(userId);
    }

    /**
     * 根据username获取对象
     *
     * @param username
     * @return
     */
    @Override
    public UserInfo getUserInfoByUsername(String username) {
        return this.userInfoMapper.selectByUsername(username);
    }

    /**
     * 根据username修改
     *
     * @param bean
     * @param username
     * @return
     */
    @Override
    public Integer updateUserInfoByUsername(UserInfo bean, String username) {
        return this.userInfoMapper.updateByUsername(bean, username);
    }

    /**
     * 根据username删除
     *
     * @param username
     * @return
     */
    @Override
    public Integer deleteUserInfoByUsername(String username) {
        return this.userInfoMapper.deleteByUsername(username);
    }

    /**
     * 根据Email获取对象
     */
    @Override
    public UserInfo getUserInfoByEmail(String email) {
        return this.userInfoMapper.selectByEmail(email);
    }

    /**
     * 根据Email修改
     */
    @Override
    public Integer updateUserInfoByEmail(UserInfo bean, String email) {
        return this.userInfoMapper.updateByEmail(bean, email);
    }

    /**
     * 根据Email删除
     */
    @Override
    public Integer deleteUserInfoByEmail(String email) {
        return this.userInfoMapper.deleteByEmail(email);
    }

    /**
     * 根据NickName获取对象
     */
    @Override
    public UserInfo getUserInfoByNickName(String nickName) {
        return this.userInfoMapper.selectByNickName(nickName);
    }

    /**
     * 根据NickName修改
     */
    @Override
    public Integer updateUserInfoByNickName(UserInfo bean, String nickName) {
        return this.userInfoMapper.updateByNickName(bean, nickName);
    }

    /**
     * 根据NickName删除
     */
    @Override
    public Integer deleteUserInfoByNickName(String nickName) {
        return this.userInfoMapper.deleteByNickName(nickName);
    }

    @Override
    public SessionWebUserDto login(String username, String password, String ip) {

        UserInfo userInfo = this.userInfoMapper.selectByUsername(username);

        String email = username;
        if (userInfo == null) {
            userInfo = this.userInfoMapper.selectByEmail(email);
        }


        if (null == userInfo || !userInfo.getPassword().equals(password)) {
            throw new BusinessException("用户名或密码错误!!!");
        }

        if (UserStatusEnum.DISABLE.getStatus().equals(userInfo.getStatus())) {
            throw new BusinessException("账号已禁用");
        }
        UserInfo updateInfo = new UserInfo();
        updateInfo.setLastLoginTime(new Date());
        updateInfo.setLastLoginIp(ip);
        Map<String, String> addressInfo = getIpAddress(ip);
        String pro = addressInfo.get("pro");
        pro = StringTools.isEmpty(pro) ? Constants.PRO_UNKNOWN : pro;
        updateInfo.setLastLoginIpAddress(pro);
        this.userInfoMapper.updateByUserId(updateInfo, userInfo.getUserId());
        SessionWebUserDto sessionWebUserDto = new SessionWebUserDto();
        sessionWebUserDto.setUsername(userInfo.getUsername());
        sessionWebUserDto.setNickName(userInfo.getNickName());
        sessionWebUserDto.setProvince(pro);
        sessionWebUserDto.setUserId(userInfo.getUserId());

        // 判断是否为管理员
        if (userInfo.getStatus() == -1) {
            sessionWebUserDto.setAdmin(true);
        } else {
            sessionWebUserDto.setAdmin(false);
        }
//        if (!StringTools.isEmpty(webConfig.getAdminEmails()) && ArrayUtils.contains(webConfig.getAdminEmails().split(","), userInfo.getEmail())) {
//            sessionWebUserDto.setAdmin(true);
//        } else {
//            sessionWebUserDto.setAdmin(false);
//        }
        return sessionWebUserDto;
    }

    public Map<String, String> getIpAddress(String ip) {
        Map<String, String> addressInfo = new HashMap<>();
        try {
            String url = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + ip;
            String responseJson = OKHttpUtils.getRequest(url);
            addressInfo = JsonUtils.convertJson2Obj(responseJson, Map.class);
            return addressInfo;
        } catch (Exception e) {
            logger.error("获取ip所在地失败");
        }
        return addressInfo;
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void register(String email, String nickName, String password) {
//        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);
//        if (null != userInfo) {
////            throw new BusinessException("邮箱账号已经存在");
//            throw new BusinessException("用户名已经存在");
//        }
//        UserInfo nickNameUser = this.userInfoMapper.selectByNickName(nickName);
//        if (null != nickNameUser) {
//            throw new BusinessException("昵称已经存在");
//        }
//
//        //校验邮箱验证码
////        emailCodeService.checkCode(email, emailCode);
//
//        String userId = StringTools.getRandomNumber(Constants.LENGTH_10);
//        userInfo = new UserInfo();
//        userInfo.setUserId(userId);
//        userInfo.setNickName(nickName);
//        userInfo.setEmail(email);
//        userInfo.setPassword(StringTools.encodeByMD5(password));
//        userInfo.setJoinTime(new Date());
//        userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
//        userInfo.setTotalIntegral(0);
//        userInfo.setCurrentIntegral(0);
//        this.userInfoMapper.insert(userInfo);
//        updateUserIntegral(userId, UserIntegralOperTypeEnum.REGISTER, UserIntegralChangeTypeEnum.ADD.getChangeType(), 5);
//
//
//        //记录消息
//        UserMessage userMessage = new UserMessage();
//        userMessage.setReceivedUserId(userId);
//        userMessage.setMessageType(MessageTypeEnum.SYS.getType());
//        userMessage.setCreateTime(new Date());
//        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
//        userMessage.setMessageContent(SysCacheUtils.getSysSetting().getRegisterSetting().getRegisterWelcomInfo());
//        userMessageService.add(userMessage);
//
//    }


    // new register
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String username, String nickName, String password) {
        UserInfo userInfo = this.userInfoMapper.selectByUsername(username);
        if (null != userInfo) {
//            throw new BusinessException("邮箱账号已经存在");
            throw new BusinessException("用户名已经存在");
        }
        UserInfo nickNameUser = this.userInfoMapper.selectByNickName(nickName);
        if (null != nickNameUser) {
            throw new BusinessException("昵称已经存在");
        }

        //校验邮箱验证码
//        emailCodeService.checkCode(email, emailCode);

        /* TODO 新增用户 */
        String userId = StringTools.getRandomNumber(Constants.LENGTH_10);
        userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setNickName(nickName);
        userInfo.setUsername(username);
//        userInfo.setEmail(email);
        userInfo.setPassword(StringTools.encodeByMD5(password));
        userInfo.setJoinTime(new Date());
        userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
        userInfo.setTotalIntegral(0);
        userInfo.setCurrentIntegral(0);
        this.userInfoMapper.insert(userInfo);
        updateUserIntegral(userId, UserIntegralOperTypeEnum.REGISTER, UserIntegralChangeTypeEnum.ADD.getChangeType(), 5);

        /*TODO 设置用户隐私设置信息*/
        UserPrivacy userPrivacy = new UserPrivacy();
        userPrivacy.setUserId(userId);
        UserPrivacyDto userPrivacyDto = new UserPrivacyDto();
        // 隐私设置均为所有人可见
        userPrivacyDto.setArticleView(UserPrivacyEnum.EVERYONE_VISIBLE.getCode());
        userPrivacyDto.setCommentView(UserPrivacyEnum.EVERYONE_VISIBLE.getCode());
        userPrivacyDto.setLikeView(UserPrivacyEnum.EVERYONE_VISIBLE.getCode());
        userPrivacyDto.setCollectView(UserPrivacyEnum.EVERYONE_VISIBLE.getCode());
        userPrivacyDto.setFollowView(UserPrivacyEnum.EVERYONE_VISIBLE.getCode());
        userPrivacyDto.setFansView(UserPrivacyEnum.EVERYONE_VISIBLE.getCode());
        userPrivacy.setJsonPrivacy(JsonUtils.convertObj2Json(userPrivacyDto));
        userPrivacyService.add(userPrivacy);

        /* TODO 设置用户权限信息 */
        UserPermission userPermission = new UserPermission();
        userPermission.setUserId(userId);
        // 设置用户权限为可发送文章、可发表评论
//        UserPermissionDto userPermissionDto = new UserPermissionDto();
        List<String> havePerm = new ArrayList<>();
        for (UserPermissionEnum enumIte : UserPermissionEnum.getAllItem()) {
            havePerm.add(enumIte.getCode());
        }

//        userPermissionDto.setPostArticle(true);
//        userPermissionDto.setPostComment(true);
        userPermission.setJsonPermission(JsonUtils.convertObj2Json(havePerm));
        userPermissionService.add(userPermission);

        //记录消息
        UserMessage userMessage = new UserMessage();
        userMessage.setReceivedUserId(userId);
        userMessage.setMessageType(MessageTypeEnum.SYS.getType());
        userMessage.setCreateTime(new Date());
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        userMessage.setMessageContent(SysCacheUtils.getSysSetting().getRegisterSetting().getRegisterWelcomInfo());
        userMessageService.add(userMessage);

    }

    /*TODO: 修改密码，根据Email*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPwdByEmail(String email, String password, String emailCode) {
        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);
        if (null == userInfo) {
//            throw new BusinessException("邮箱账号不存在");
            throw new BusinessException("用户名不存在");
        }
        //校验邮箱验证码
        emailCodeService.checkCode(email, emailCode);

        UserInfo updateInfo = new UserInfo();
        updateInfo.setPassword(StringTools.encodeByMD5(password));
        this.userInfoMapper.updateByEmail(updateInfo, email);
    }

    /*TODO: 修改密码，根据oldPassword*/
    @Override
    public void resetPwdByOldPwd(String username, String oldPassword, String newPassword) {
        UserInfo userInfo = this.userInfoMapper.selectByUsername(username);
        if (null == userInfo) {
            throw new BusinessException("用户名不存在");
        }
        if (!userInfo.equals(oldPassword)) {
            throw new BusinessException("旧密码不对");
        }

        UserInfo updateInfo = new UserInfo();
        updateInfo.setPassword(newPassword);
        this.userInfoMapper.updateByUsername(updateInfo, username);
    }


//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void resetPwd(String email, String password, String emailCode) {
//        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);
//        if (null == userInfo) {
////            throw new BusinessException("邮箱账号不存在");
//            throw new BusinessException("用户名不存在");
//        }
//        //校验邮箱验证码
////        emailCodeService.checkCode(email, emailCode);
//
//        UserInfo updateInfo = new UserInfo();
//        updateInfo.setPassword(StringTools.encodeByMD5(password));
//        this.userInfoMapper.updateByEmail(updateInfo, email);
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserIntegral(String userId, UserIntegralOperTypeEnum operTypeEnum, Integer changeType, Integer integral) {
        integral = changeType * integral;

        if (integral == 0) {
            return;
        }

        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        if (UserIntegralChangeTypeEnum.REDUCE.getChangeType().equals(changeType) && userInfo.getCurrentIntegral() + integral < 0) {
            integral = changeType * userInfo.getCurrentIntegral();
        }

        UserIntegralRecord record = new UserIntegralRecord();
        record.setUserId(userId);
        record.setOperType(operTypeEnum.getOperType());
        record.setCreateTime(new Date());
        record.setIntegral(integral);
        this.userIntegralRecordMapper.insert(record);

        Integer count = this.userInfoMapper.updateIntegral(userId, integral);
        if (count == 0) {
            throw new BusinessException("更新用户积分失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserInfo userInfo, MultipartFile avatar) {
        userInfoMapper.updateByUserId(userInfo, userInfo.getUserId());
        if (avatar != null) {
            fileUtils.uploadFile2Local(avatar, FileUploadTypeEnum.AVATAR, userInfo.getUserId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserStatus(Integer status, String userId) {
        if (UserStatusEnum.DISABLE.getStatus().equals(status)) {
            forumArticleMapper.updateStatusBatchByUserId(ArticleStatusEnum.DEL.getStatus(), userId);
            forumCommentMapper.updateStatusBatchByUserId(CommentStatusEnum.DEL.getStatus(), userId);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setStatus(status);
        userInfoMapper.updateByUserId(userInfo, userId);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(String userId, String message, Integer integral) {
        UserMessage userMessage = new UserMessage();
        userMessage.setReceivedUserId(userId);
        userMessage.setMessageType(MessageTypeEnum.SYS.getType());
        userMessage.setCreateTime(new Date());
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        userMessage.setMessageContent(message);
        userMessageService.add(userMessage);
        if (integral != null && integral != 0) {
            updateUserIntegral(userId, UserIntegralOperTypeEnum.ADMIN, integral > 0 ? UserIntegralChangeTypeEnum.ADD.getChangeType() :
                    UserIntegralChangeTypeEnum.REDUCE.getChangeType(), integral);
        }
    }

    /**
     * 绑定邮箱 / 解绑邮箱
     *
     * @param email
     * @param emailCode
     * @param type
     */
    @Override
    public void bindEmail(String userId, String email, String emailCode, Integer type) {

        UserInfo userInfo = userInfoMapper.selectByEmail(email);
        // type 0: 绑定邮箱 1: 解绑邮箱
        if (type == 0) {
            if (null != userInfo) {
                throw new BusinessException("邮箱已经存在");
            }
            //校验邮箱验证码
            emailCodeService.checkCode(email, emailCode);

            UserInfo updateUser = userInfoMapper.selectByUserId(userId);

            updateUser.setEmail(email);
            userInfoMapper.updateByUserId(updateUser, userId);

        } else {
            //校验邮箱验证码
            emailCodeService.checkCode(email, emailCode);

            // 邮箱设空
            userInfo.setEmail("");
            userInfoMapper.updateByUserId(userInfo, userId);

        }
    }
}