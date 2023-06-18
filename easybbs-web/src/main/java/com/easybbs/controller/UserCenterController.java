package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.dto.SessionWebUserDto;
import com.easybbs.entity.dto.UserMessageCountDto;
import com.easybbs.entity.dto.UserPrivacyDto;
import com.easybbs.entity.enums.ArticleStatusEnum;
import com.easybbs.entity.enums.MessageTypeEnum;
import com.easybbs.entity.enums.ResponseCodeEnum;
import com.easybbs.entity.enums.UserStatusEnum;
import com.easybbs.entity.po.FollowRecord;
import com.easybbs.entity.po.ForumArticle;
import com.easybbs.entity.po.UserInfo;
import com.easybbs.entity.po.UserPrivacy;
import com.easybbs.entity.query.*;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.entity.vo.web.ForumArticleVO;
import com.easybbs.entity.vo.web.UserFollowVo;
import com.easybbs.entity.vo.web.UserInfoVO;
import com.easybbs.entity.vo.web.UserMessageVO;
import com.easybbs.exception.BusinessException;
import com.easybbs.service.*;
import com.easybbs.utils.CopyTools;
import com.easybbs.utils.JsonUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ucenter")
public class UserCenterController extends BaseController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private ForumArticleService forumArticleService;

    @Resource
    private UserMessageService userMessageService;

    @Resource
    private LikeRecordService likeRecordService;

    @Resource
    private CollectRecordService collectRecordService;

    @Resource
    private FollowRecordService followRecordService;

    @Resource
    private UserIntegralRecordService userIntegralRecordService;

    @Resource
    private UserPrivacyService userPrivacyService;

    @RequestMapping("/getUserInfo")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO getUserInfo(HttpSession session, @VerifyParam(required = true) String userId) {

        SessionWebUserDto sessionWebUserDto = getUserInfoFromSession(session);

        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        if (null == userInfo || UserStatusEnum.DISABLE.getStatus().equals(userInfo.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        ForumArticleQuery articleQuery = new ForumArticleQuery();
        articleQuery.setUserId(userId);
        articleQuery.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        Integer postCount = forumArticleService.findCountByParam(articleQuery);
        UserInfoVO userInfoVO = CopyTools.copy(userInfo, UserInfoVO.class);
        // 设置用户类型
        userInfoVO.setType(userInfo.getStatus());
        userInfoVO.setPostCount(postCount);

        LikeRecordQuery recordQuery = new LikeRecordQuery();
        recordQuery.setAuthorUserId(userId);
        // 查询点赞数
        Integer likeCount = likeRecordService.findCountByParam(recordQuery);
        // TODO 新增收藏功能
        CollectRecordQuery collectRecordQuery = new CollectRecordQuery();
        collectRecordQuery.setAuthorUserId(userId);
        // 查询收藏数
        Integer collectCount = collectRecordService.findCountByParam(collectRecordQuery);

        // TODO 新增用户关注功能
        FollowRecordQuery followRecordQuery = new FollowRecordQuery();
        // TODO 关注量
        followRecordQuery.setUserId(userId);
        // 查询关注数
        Integer followCount = followRecordService.findCountByParam(followRecordQuery);
        // TODO 粉丝量
        followRecordQuery = new FollowRecordQuery();
        followRecordQuery.setFollowedUserId(userId);
        // 查询粉丝数
        Integer fansCount = followRecordService.findCountByParam(followRecordQuery);

        // TODO 判断当前用户是否已关注
        if (sessionWebUserDto != null) {
            FollowRecord followRecord = followRecordService.getUserFollowRecordByUserIdAndFollowedUserId(sessionWebUserDto.getUserId(), userId);
            if (followRecord != null) {
                userInfoVO.setHaveFollow(true);
            }
        }

        // TODO 新增查询用户隐私设置
        UserPrivacy userPrivacy = userPrivacyService.getUserPrivacyByUserId(userId);
        // 查询隐私设置
        UserPrivacyDto userPrivacyDto = JsonUtils.convertJson2Obj(userPrivacy.getJsonPrivacy(), UserPrivacyDto.class);
        userInfoVO.setUserPrivacyDto(userPrivacyDto);

        userInfoVO.setLikeCount(likeCount);
        userInfoVO.setCollectCount(collectCount);
        userInfoVO.setFollowCount(followCount);
        userInfoVO.setFansCount(fansCount);

        userInfoVO.setCurrentIntegral(userInfo.getCurrentIntegral());
        return getSuccessResponseVO(userInfoVO);
    }

    @RequestMapping("/updateUserInfo")
    @GlobalInterceptor(checkParams = true, checkLogin = true)
    public ResponseVO updateUserInfo(HttpSession session, Integer sex,
                                     @VerifyParam(max = 100) String personDescription,
                                     MultipartFile avatar) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userDto.getUserId());
        userInfo.setSex(sex);
        userInfo.setPersonDescription(personDescription);
        userInfoService.updateUserInfo(userInfo, avatar);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadUserIntegralRecord")
    @GlobalInterceptor(checkParams = true, checkLogin = true)
    public ResponseVO loadUserIntegralRecord(HttpSession session, Integer pageNo, String createTimeStart, String createTimeEnd) {
        UserIntegralRecordQuery recordQuery = new UserIntegralRecordQuery();
        recordQuery.setUserId(getUserInfoFromSession(session).getUserId());
        recordQuery.setPageNo(pageNo);
        recordQuery.setCreateTimeStart(createTimeStart);
        recordQuery.setCreateTimeEnd(createTimeEnd);
        recordQuery.setOrderBy("record_id desc");
        PaginationResultVO resultVO = userIntegralRecordService.findListByPage(recordQuery);
        return getSuccessResponseVO(resultVO);
    }

    /**
     * TODO 新增了隐私设置功能
     *
     * @param session
     * @param userId
     * @param type
     * @return
     */
    @RequestMapping("/loadUserArticle")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO loadUserArticle(HttpSession session, @VerifyParam(required = true) String userId, @VerifyParam(required = true) Integer type) {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        if (null == userInfo || UserStatusEnum.DISABLE.getStatus().equals(userInfo.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        // 当前登录用户，如果登录了的话
        SessionWebUserDto userDto = getUserInfoFromSession(session);

        /*这里进行过滤*/
        boolean isFilter = true;
        if (userDto != null) {
            isFilter = privacyFilter(userDto.getUserId(), userId, type);
        } else {
            isFilter = privacyFilter2(userId, type);
        }

        PaginationResultVO<ForumArticle> result = null;
        if (isFilter) {
            // 过滤的话就返回一个空
            result = new PaginationResultVO<ForumArticle>(0, 0, 0, 0, new ArrayList<>());

        } else {

            ForumArticleQuery articleQuery = new ForumArticleQuery();
            articleQuery.setOrderBy("post_time desc");
            if (type == 0) {
                articleQuery.setUserId(userId);
            } else if (type == 1) {
                articleQuery.setCommentUserId(userId);
            } else if (type == 2) {
                articleQuery.setLikeUserId(userId);
            }
            // TODO 新增收藏夹功能
            else if (type == 3) {
                articleQuery.setCollectUserId(userId);
            }
            //当前用户展示待审核
            if (type == 0 && userDto != null && userDto.getUserId().equals(userId)) {
                articleQuery.setCurrentUserId(userDto.getUserId());
            } else {
                //            articleQuery.setStatus(ArticleStatusEnum.AUDIT.getStatus());
                // status 是审核，status2 是发布
                articleQuery.setStatus2(ArticleStatusEnum.POST.getStatus());
            }
            result = forumArticleService.findListByPage(articleQuery);

        }

        // TODO 这里也得封装一下管理员type
        PaginationResultVO<ForumArticleVO> favo = convert2PaginationVO(result, ForumArticleVO.class);
        for (ForumArticleVO forumArticleVO : favo.getList()) {
            UserInfo uinfo = userInfoService.getUserInfoByUserId(forumArticleVO.getUserId());
            forumArticleVO.setType(uinfo.getStatus());
        }
//        return getSuccessResponseVO(convert2PaginationVO(result, ForumArticleVO.class));
        return getSuccessResponseVO(favo);
    }


    // TODO 新增查询 (关注、粉丝) 功能

    /**
     * 查询 关注、粉丝 功能
     *
     * @param session
     * @param userId
     * @return
     */
    @RequestMapping("/loadUserFollow")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO userFollow(HttpSession session, @VerifyParam(required = true) String userId,
                                 @VerifyParam(required = true) Integer type) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);

        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        if (null == userInfo || UserStatusEnum.DISABLE.getStatus().equals(userInfo.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }

        /*这里进行过滤*/
        boolean isFilter = true;
        if (userDto != null) {
            isFilter = privacyFilter(userDto.getUserId(), userId, type);
        } else {
            isFilter = privacyFilter2(userId, type);
        }

        PaginationResultVO<UserFollowVo> ufvResult = new PaginationResultVO<>();

        if (isFilter) {
            ufvResult = new PaginationResultVO<>(0, 0, 0, 0, new ArrayList<>());
        } else {

            FollowRecordQuery followRecordQuery = new FollowRecordQuery();
            followRecordQuery.setOrderBy("create_time desc");
            // type (4: 关注, 5: 粉丝)
            if (type == 4) {
                followRecordQuery.setUserId(userId);
            } else if (type == 5) {
                followRecordQuery.setFollowedUserId(userId);
            }
            PaginationResultVO<FollowRecord> result = followRecordService.findListByPage(followRecordQuery);

            List<String> userIds = new ArrayList<>();

            for (FollowRecord followRecord : result.getList()) {
                // 获取所有关注用户的userId
                if (type == 4)
                    userIds.add(followRecord.getFollowedUserId());
                    // 获取所有粉丝的userId
                else if (type == 5)
                    userIds.add(followRecord.getUserId());
            }

            List<UserFollowVo> ufList = new ArrayList<>();
            for (String uId : userIds) {
                UserInfo uInfo = userInfoService.getUserInfoByUserId(uId);
                if (uInfo == null) {
                    continue;
                }
                UserFollowVo ufo = new UserFollowVo();
                ufo.setUserId(uInfo.getUserId());
                ufo.setNickName(uInfo.getNickName());
                ufo.setPersonDescription(uInfo.getPersonDescription());
                ufo.setSex(uInfo.getSex());
                // 如果没有登录就直接跳过，不就行判断是否关注操作
                if (userDto == null) {
                    ufList.add(ufo);
                    continue;
                }
                // 当前用户是登录状态
                FollowRecord followRecord = followRecord = followRecordService.getUserFollowRecordByUserIdAndFollowedUserId(userDto.getUserId(), uId);
                if (followRecord != null) {
                    ufo.setHaveFollow(true);
                }
                ufList.add(ufo);
            }
            // TODO 这里只是把关注、粉丝的数据查出来而已，还需要进行封装用户的其他信息。。。
//        PaginationResultVO<FollowRecord> result
//            PaginationResultVO<UserFollowVo> ufvResult = new PaginationResultVO<>();
            ufvResult = new PaginationResultVO<>();
            ufvResult.setList(ufList);
            ufvResult.setPageNo(result.getPageNo());
            ufvResult.setPageSize(result.getPageSize());
            ufvResult.setPageTotal(result.getPageTotal());
            ufvResult.setTotalCount(result.getTotalCount());
        }
        return getSuccessResponseVO(ufvResult);

    }

    // TODO 新增用户关注功能

    /**
     * 新增收藏功能。。。
     *
     * @param session
     * @param followedUserId
     * @return
     */
    @RequestMapping("/doFollow")
//    @GlobalInterceptor(checkLogin = true, checkParams = true, frequencyType = UserOperFrequencyTypeEnum.DO_FOLLOW)
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVO doCollect(HttpSession session, @VerifyParam(required = true) String followedUserId) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        followRecordService.doFollow(userDto.getUserId(), followedUserId);
        return getSuccessResponseVO(null);
    }

    /**
     * 关注或者取消关注用户功能
     *
     * @param session  当前登录用户
     * @param targetId 目标用户
     * @param type     0；取消关注 1：关注
     * @return
     */
    @RequestMapping("/unOrCollect")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO unOrCollect(HttpSession session,
                                  @VerifyParam(required = true) String targetId,
                                  Integer type) {
        if (type != 0 && type != 1) {
            throw new BusinessException("非法参数，不予受理！");
        }
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (type == 1) {
            followRecordService.doFollow(userDto.getUserId(), targetId);
        } else if (type == 0) {
            followRecordService.deleteUserFollowRecordByUserIdAndFollowedUserId(userDto.getUserId(), targetId);
        }

        return getSuccessResponseVO(null);
    }


    @RequestMapping("/getMessageCount")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO getMessageCount(HttpSession session) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (null == userDto) {
            return getSuccessResponseVO(new UserMessageCountDto());
        }
        return getSuccessResponseVO(userMessageService.getUserMessageCount(userDto.getUserId()));
    }

    /**
     * 消息列表
     *
     * @param session
     * @return
     */
    @RequestMapping("/loadMessageList")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVO loadMessageList(HttpSession session, @VerifyParam(required = true) String code, Integer pageNo) {
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getByCode(code);
        if (null == messageTypeEnum) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        UserMessageQuery userMessageQuery = new UserMessageQuery();
        userMessageQuery.setPageNo(pageNo);
        userMessageQuery.setReceivedUserId(userDto.getUserId());
        userMessageQuery.setMessageType(messageTypeEnum.getType());
        userMessageQuery.setOrderBy("message_id desc");
        PaginationResultVO result = userMessageService.findListByPage(userMessageQuery);
        if (pageNo == null || pageNo == 1) {
            userMessageService.readMessageByType(userDto.getUserId(), messageTypeEnum.getType());
        }
        PaginationResultVO resultVO = convert2PaginationVO(result, UserMessageVO.class);
        return getSuccessResponseVO(resultVO);
    }

    /**
     * 绑定邮箱
     *
     * @param session
     * @param email
     * @param emailCode
     * @param type
     * @return
     */
    @RequestMapping("/bindEmail")
    public ResponseVO bindEmail(HttpSession session,
                                @VerifyParam(required = true) String email,
                                @VerifyParam(required = true) String emailCode,
                                @VerifyParam(required = true) Integer type) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);

        if (null == userDto) {
            throw new BusinessException("请先登录!");
        }

        userInfoService.bindEmail(userDto.getUserId(), email, emailCode, type);


        return getSuccessResponseVO(null);
    }

    @RequestMapping("loadPrivacy")
    public ResponseVO loadPrivacy(HttpSession session) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);

        if (null == userDto) {
            throw new BusinessException("请先登录!");
        }
        UserPrivacy userPrivacy = userPrivacyService.getUserPrivacyByUserId(userDto.getUserId());

        return getSuccessResponseVO(JsonUtils.convertObj2Json(userPrivacy));
    }


    @RequestMapping("/privacySetting")
    public ResponseVO privacySetting(HttpSession session,
                                     @VerifyParam(required = true) UserPrivacyDto userPrivacyDto) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);

        if (null == userDto) {
            throw new BusinessException("请先登录!");
        }

        UserPrivacy userPrivacy = new UserPrivacy();
        userPrivacy.setUserId(userDto.getUserId());
        userPrivacy.setJsonPrivacy(JsonUtils.convertObj2Json(userPrivacyDto));

        userPrivacyService.updateUserPrivacyByUserId(userPrivacy, userDto.getUserId());

        return getSuccessResponseVO(null);
    }

    // 判断是否过滤查询操作（登录过滤操作）
    private boolean privacyFilter(String currentUserId, String targetUserId, Integer type) {
        // 如果当前用户id就目标用户id的话就直接放行
        if (currentUserId != null && currentUserId.equals(targetUserId)) {
            return false;
        }
        // 查询当前当前用户是否关注了目标用户
        FollowRecord fr = followRecordService.getUserFollowRecordByUserIdAndFollowedUserId(currentUserId, targetUserId);

        UserPrivacy privacy = userPrivacyService.getUserPrivacyByUserId(targetUserId);
        UserPrivacyDto userPrivacyDto = JsonUtils.convertJson2Obj(privacy.getJsonPrivacy(), UserPrivacyDto.class);
        Integer code = getPrivacyCode(userPrivacyDto, type);

        if (code == 1) {
            System.out.println("判断当前用户是否关注了目标用户...: " + fr);
            return fr == null;
        } else if (code == 2) {
            return true;
        }
        return false;

    }

    // 判断是否过滤操作（非登录过滤操作）
    private boolean privacyFilter2(String targetUserId, Integer type) {
        UserPrivacy privacy = userPrivacyService.getUserPrivacyByUserId(targetUserId);
        UserPrivacyDto userPrivacyDto = JsonUtils.convertJson2Obj(privacy.getJsonPrivacy(), UserPrivacyDto.class);
        Integer code = getPrivacyCode(userPrivacyDto, type);
        if (code == 0) {
            return false;
        } else {
            return true;
        }

    }

    // 根据type返回隐私code
    private Integer getPrivacyCode(UserPrivacyDto privacyDto, Integer type) {
        Integer code = 2;
        if (type == 0) {
            code = privacyDto.getArticleView();
        } else if (type == 1) {
            code = privacyDto.getCommentView();
        } else if (type == 2) {
            code = privacyDto.getLikeView();
        } else if (type == 3) {
            code = privacyDto.getCollectView();
        } else if (type == 4) {
            code = privacyDto.getFollowView();
        } else if (type == 5) {
            code = privacyDto.getFansView();
        }
        return code;
    }

}
