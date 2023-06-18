package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.dto.SessionWebUserDto;
import com.easybbs.entity.enums.*;
import com.easybbs.entity.po.*;
import com.easybbs.entity.query.ForumCommentQuery;
import com.easybbs.entity.query.SensitiveWordQuery;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.entity.vo.web.ForumCommentVo;
import com.easybbs.exception.BusinessException;
import com.easybbs.service.*;
import com.easybbs.utils.CopyTools;
import com.easybbs.utils.JsonUtils;
import com.easybbs.utils.StringTools;
import com.easybbs.utils.SysCacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class ForumCommentController extends BaseController {

    @Resource
    private ForumCommentService forumCommentService;

    @Resource
    private LikeRecordService likeRecordService;

    @Resource
    private SensitiveWordService sensitiveWordService;

    @Resource
    private UserPermissionService userPermissionService;

    @Resource
    private UserInfoService userInfoService;


    @RequestMapping("/loadComment")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO loadComment(HttpSession session, @VerifyParam(required = true) String articleId, Integer pageNo, Integer orderType) {
        if (!SysCacheUtils.getSysSetting().getCommentSetting().getCommentOpen()) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        ForumCommentQuery commentQuery = new ForumCommentQuery();
        commentQuery.setArticleId(articleId);
        commentQuery.setLoadChildren(true);
        String orderBy = orderType == null || orderType == 0 ? "good_count desc,comment_id asc" : "comment_id desc";
        commentQuery.setOrderBy("top_type desc," + orderBy);
        commentQuery.setPageNo(pageNo);

        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (userDto != null) {
            commentQuery.setQueryLikeType(true);
            commentQuery.setCurrentUserId(userDto.getUserId());
        } else {
            commentQuery.setStatus(CommentStatusEnum.AUDIT.getStatus());
        }
        commentQuery.setPageSize(PageSize.SIZE50.getSize());
        commentQuery.setStatus(CommentStatusEnum.AUDIT.getStatus());
        commentQuery.setpCommentId(0);


        PaginationResultVO<ForumComment> psvf = forumCommentService.findListByPage(commentQuery);
        // 要返回前端的
        PaginationResultVO<ForumCommentVo> fcvPrv = convert2PaginationVO(psvf, ForumCommentVo.class);


        List<ForumComment> psvfList = psvf.getList();
        List<ForumCommentVo> prvList = fcvPrv.getList();
        for (int i = 0; i < psvfList.size(); i++) {
            // 设置一级评论用户类型
            ForumComment forumComment = psvfList.get(i);
            UserInfo userinfo = userInfoService.getUserInfoByUserId(forumComment.getUserId());
            ForumCommentVo forumCommentVo = prvList.get(i);
            forumCommentVo.setType(userinfo.getStatus());


            // 设置二级评论用户类型
            if (forumComment.getChildren() != null && forumComment.getChildren().size() > 0) {
                List<ForumCommentVo> forumCommentVos = CopyTools.copyList(forumComment.getChildren(), ForumCommentVo.class);
                for (ForumCommentVo commentVo : forumCommentVos) {
                    UserInfo ui = userInfoService.getUserInfoByUserId(commentVo.getUserId());
                    commentVo.setType(ui.getStatus());
                }
                forumCommentVo.setChildren(forumCommentVos);
            }

        }

        return getSuccessResponseVO(fcvPrv);
    }

    /**
     * 发表评论
     *
     */
    @RequestMapping("/postComment")
    @GlobalInterceptor(checkLogin = true, checkParams = true, frequencyType = UserOperFrequencyTypeEnum.POST_COMMENT)
    public ResponseVO postComment(HttpSession session,
                                  @VerifyParam(required = true) String articleId,
                                  @VerifyParam(required = true) Integer pCommentId,
                                  @VerifyParam(min = 5, max = 800) String content,
                                  String replyUserId, MultipartFile image) {
        //是否关闭评论
        if (!SysCacheUtils.getSysSetting().getCommentSetting().getCommentOpen()) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        //单独判断内容和图片
        if (image == null && StringTools.isEmpty(content)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        SessionWebUserDto userDto = getUserInfoFromSession(session);

        // 发表评论需要进行权限校验
        checkPermission(userDto.getUserId(), UserPermissionEnum.POST_COMMENT);

        ForumComment comment = new ForumComment();
        content = StringTools.escapeHtml(content);
        comment.setUserId(userDto.getUserId());
        comment.setNickName(userDto.getNickName());
        comment.setUserIpAddress(userDto.getProvince());
        comment.setpCommentId(pCommentId);
        comment.setArticleId(articleId);

        // 原评论，未处理过的
        String originalContent = content;
        // 敏感词屏蔽处理后
        String sensitiveContent = sensitiveWordHandle(content);

        comment.setOriginalContent(originalContent);
        comment.setContent(sensitiveContent);

        comment.setReplyUserId(replyUserId);
        comment.setTopType(CommentTopTypeEnum.NO_TOP.getType());
        forumCommentService.postComment(comment, image);
        if (pCommentId != 0) {
            ForumCommentQuery commentQuery = new ForumCommentQuery();
            commentQuery.setArticleId(articleId);
            commentQuery.setpCommentId(pCommentId);
            commentQuery.setOrderBy("top_type desc,comment_id asc");
            commentQuery.setCurrentUserId(userDto.getUserId());
            List<ForumComment> children = forumCommentService.findListByParam(commentQuery);
            return getSuccessResponseVO(children);
        }
        return getSuccessResponseVO(comment);
    }

    @RequestMapping("/doLike")
    @GlobalInterceptor(checkLogin = true, checkParams = true, frequencyType = UserOperFrequencyTypeEnum.DO_LIKE)
    public ResponseVO doLike(HttpSession session,
                             @VerifyParam(required = true) Integer commentId) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        String objectId = String.valueOf(commentId);
        likeRecordService.doLike(objectId, userDto.getUserId(), userDto.getNickName(), OperRecordOpTypeEnum.COMMENT_LIKE);
        LikeRecord userOperRecord = likeRecordService.getUserOperRecordByObjectIdAndUserIdAndOpType(objectId, userDto.getUserId(),
                OperRecordOpTypeEnum.COMMENT_LIKE.getType());
        ForumComment comment = forumCommentService.getForumCommentByCommentId(commentId);
        comment.setLikeType(userOperRecord == null ? null : 1);
        return getSuccessResponseVO(comment);
    }

    @RequestMapping("/changeTopType")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVO changeTopType(HttpSession session,
                                    @VerifyParam(required = true) Integer commentId, @VerifyParam(required = true) Integer topType) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        forumCommentService.changeTopType(userDto.getUserId(), commentId, topType);
        return getSuccessResponseVO(null);
    }

    /**
     * 敏感词屏蔽处理
     *
     * @param comment
     * @return
     */
    private String sensitiveWordHandle(String comment) {
        SensitiveWordQuery query = new SensitiveWordQuery();
        // 启用的屏蔽词
        query.setStatus(1);
        List<SensitiveWord> wordList = sensitiveWordService.findListByParam(query);
        List<String> words = wordList.stream()
                .map(sensitiveWord -> sensitiveWord.getWord())
                .collect(Collectors.toList());

        // 遍历敏感词列表，将其中每个词都替换为相应数量的 *
        for (String word : words) {
            // 构造正则表达式，将敏感词替换为相应数量的 *
            String asterisks = StringUtils.repeat("*", word.length());
            comment = comment.replaceAll(word, asterisks);
        }

        return comment;

    }

    /**
     * 权限校验方法...
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
            throw new BusinessException("不好一下，您没有【" + enumItem.getDesc() + "】的权限！");
        }
    }

}
