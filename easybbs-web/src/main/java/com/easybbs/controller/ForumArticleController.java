package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.config.WebConfig;
import com.easybbs.entity.constants.Constants;
import com.easybbs.entity.dto.SessionWebUserDto;
import com.easybbs.entity.dto.SysSetting4AuditDto;
import com.easybbs.entity.enums.*;
import com.easybbs.entity.po.*;
import com.easybbs.entity.query.ForumArticleQuery;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.entity.vo.web.FormArticleDetailVO;
import com.easybbs.entity.vo.web.FormArticleUpdateDetailVO;
import com.easybbs.entity.vo.web.ForumArticleVO;
import com.easybbs.exception.BusinessException;
import com.easybbs.service.*;
import com.easybbs.utils.CopyTools;
import com.easybbs.utils.JsonUtils;
import com.easybbs.utils.StringTools;
import com.easybbs.utils.SysCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumArticleController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ForumArticleController.class);

    @Resource
    private WebConfig webConfig;

    @Resource
    private ForumBoardService forumBoardService;

    @Resource
    private ForumArticleService forumArticleService;

    @Resource
    private ForumArticleAttachmentService forumArticleAttachmentService;

    @Resource
    private ForumArticleAttachmentDownloadService forumArticleAttachmentDownloadService;

    @Resource
    private LikeRecordService likeRecordService;

    @Resource
    private CollectRecordService collectRecordService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserPermissionService userPermissionService;

    @RequestMapping("/loadArticle")
    public ResponseVO loadArticle(HttpSession session, Integer boardId, Integer pBoardId, Integer orderType, Integer pageNo) {
        ForumArticleQuery articleQuery = new ForumArticleQuery();
        articleQuery.setBoardId(boardId == null || boardId == 0 ? null : boardId);
        articleQuery.setpBoardId(pBoardId);
        articleQuery.setPageNo(pageNo);

        SessionWebUserDto userDto = getUserInfoFromSession(session);
//        if (userDto != null) {
//            articleQuery.setCurrentUserId(userDto.getUserId());
//        } else {
////            articleQuery.setStatus(ArticleStatusEnum.AUDIT.getStatus());
//            articleQuery.setStatus(ArticleStatusEnum.POST.getStatus());
//        }
        articleQuery.setStatus2(ArticleStatusEnum.POST.getStatus());
        ArticleOrderTypeEnum orderTypeEnum = ArticleOrderTypeEnum.getByType(orderType);
        orderTypeEnum = orderTypeEnum == null ? ArticleOrderTypeEnum.HOT : orderTypeEnum;
        articleQuery.setOrderBy(orderTypeEnum.getOrderSql());
        PaginationResultVO resultVO = forumArticleService.findListByPage(articleQuery);
        // TODO 这里先封装一下这个管理管理员type
        PaginationResultVO<ForumArticleVO> favo = convert2PaginationVO(resultVO, ForumArticleVO.class);
        for (ForumArticleVO forumArticleVO : favo.getList()) {
            UserInfo uinfo = userInfoService.getUserInfoByUserId(forumArticleVO.getUserId());
            forumArticleVO.setType(uinfo.getStatus());
        }
//        return getSuccessResponseVO(convert2PaginationVO(resultVO, ForumArticleVO.class));
        return getSuccessResponseVO(favo);
    }


    @RequestMapping("/loadBoard4Post")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadBoard4Post(HttpSession session) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        Integer postType = null;
        if (!userDto.getAdmin()) {
            postType = 1;
        }
        return getSuccessResponseVO(forumBoardService.getBoardTree(postType));
    }

    /*发布文章、编辑文章接口*/
    @RequestMapping("/postArticle")
//    @GlobalInterceptor(checkLogin = true, checkParams = true, frequencyType = UserOperFrequencyTypeEnum.POST_ARTICLE)
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVO postArticle(HttpSession session,
                                  MultipartFile cover,
                                  MultipartFile attachment,
                                  Integer integral,
                                  @VerifyParam(required = true) Integer pBoardId,
                                  Integer boardId,
                                  @VerifyParam(required = true, max = 150) String title,
                                  @VerifyParam String content,
                                  String markdownContent,
                                  @VerifyParam(required = true) Integer editorType,
                                  @VerifyParam(max = 200) String summary) {

        title = StringTools.escapeTitle(title);
        SessionWebUserDto userDto = getUserInfoFromSession(session);

        // 发布文章、编辑文章都需要权限校验
        checkPermission(userDto.getUserId(), UserPermissionEnum.POST_ARTICLE);

        ForumArticle forumArticle = new ForumArticle();
        forumArticle.setpBoardId(pBoardId);
        forumArticle.setBoardId(boardId);
        forumArticle.setTitle(title);
        forumArticle.setContent(content);
        if (EditorTypeEnum.MARKDOWN.getType().equals(editorType) && StringTools.isEmpty(markdownContent)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        forumArticle.setMarkdownContent(markdownContent);
        forumArticle.setEditorType(editorType);
        forumArticle.setSummary(summary);
        forumArticle.setUserId(userDto.getUserId());
        forumArticle.setNickName(userDto.getNickName());
        forumArticle.setUserIpAddress(userDto.getProvince());
        //新发布的文章或编辑后的文章都重新设置为 未审核 未发布
        forumArticle.setStatus(ArticleStatusEnum.NO_AUDIT.getStatus());
        forumArticle.setStatus2(ArticleStatusEnum.NO_POST.getStatus());
        //附件信息
        ForumArticleAttachment forumArticleAttachment = new ForumArticleAttachment();
        forumArticleAttachment.setIntegral(integral == null ? 0 : integral);
        forumArticleService.postArticle(userDto.getAdmin(), forumArticle, forumArticleAttachment, cover, attachment);
        return getSuccessResponseVO(forumArticle.getArticleId());
    }

    @RequestMapping("/handleArticleStatus")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO postArticle(HttpSession session, String articleId, Integer status) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        String userId = userDto.getUserId();

        // 如果是修改文章为发布的话就校验是否有权限
        if (ArticleStatusEnum.POST.getStatus() == status) {
            checkPermission(userId, UserPermissionEnum.POST_ARTICLE);
        }

        String[] articleIds = articleId.split(",");

        for (String aid : articleIds) {
            ForumArticle fa = forumArticleService.getForumArticleByArticleId(aid);
            if (!fa.getUserId().equals(userId)) {
                throw new BusinessException("只能修改自己的文章状态。。。");
            }
            if (ArticleStatusEnum.NO_POST.getStatus() != status && ArticleStatusEnum.POST.getStatus() != status) {
                throw new BusinessException("文章要修改为的状态异常。。。");
            }

            // 发布文章
            if (ArticleStatusEnum.POST.getStatus() == status) {
                SysSetting4AuditDto auditStting = SysCacheUtils.getSysSetting().getAuditStting();

                if (ArticleStatusEnum.ERROR_AUDIT.getStatus().equals(fa.getStatus()) ||
                        ArticleStatusEnum.DEL.getStatus().equals(fa.getStatus())) {
                    throw new BusinessException("文章要状态异常，你这文章好像有问题啊？");
                }

                // 需要已审核才能发布
                if (auditStting.getPostAudit() && ArticleStatusEnum.NO_AUDIT.getStatus().equals(fa.getStatus())) {
                    throw new BusinessException("现在文章需要审核之后才能发布，无法操作！");
                }
            }

            // 撤销文章, 不需要校验是是否需要审核才能撤销


            fa.setStatus2(status);
            forumArticleService.updateForumArticleByArticleId(fa, aid);
        }
        return getSuccessResponseVO(null);

    }


    @RequestMapping("/getArticleDetail")
    public ResponseVO getArticleDetail(HttpSession session, String articleId) {
        SessionWebUserDto sessionWebUserDto = getUserInfoFromSession(session);

        ForumArticle forumArticle = forumArticleService.readArticle(articleId);

//        if (forumArticle == null
//                || (ArticleStatusEnum.NO_AUDIT.getStatus().equals(forumArticle.getStatus()) && (sessionWebUserDto == null || !sessionWebUserDto.getUserId().equals(forumArticle.getUserId()) && !sessionWebUserDto.getAdmin()))
//                || ArticleStatusEnum.DEL.getStatus().equals(forumArticle.getStatus())) {
//            throw new BusinessException(ResponseCodeEnum.CODE_404);
//        }

        // 当前文章不存在 || 当前文章未发布 且 当前登录用户不是作者 || 当前文章已被删除
        if (forumArticle == null
                || (ArticleStatusEnum.NO_POST.equals(forumArticle.getStatus2()) && (sessionWebUserDto == null || !sessionWebUserDto.getUserId().equals(forumArticle.getUserId())))
                || ArticleStatusEnum.DEL.getStatus().equals(forumArticle.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }


        FormArticleDetailVO detailVO = new FormArticleDetailVO();
        // TODO 这里也得封装一下这个管理员type
        ForumArticleVO fav = CopyTools.copy(forumArticle, ForumArticleVO.class);
        UserInfo uinfo = userInfoService.getUserInfoByUserId(fav.getUserId());
        fav.setType(uinfo.getStatus());
        detailVO.setForumArticle(fav);

//        if (forumArticle.getAttachmentType() == 1) {
//            ForumArticleAttachmentQuery articleAttachmentQuery = new ForumArticleAttachmentQuery();
//            articleAttachmentQuery.setArticleId(forumArticle.getArticleId());
//            List<ForumArticleAttachment> forumArticleAttachmentList = forumArticleAttachmentService.findListByParam(articleAttachmentQuery);
//            if (!forumArticleAttachmentList.isEmpty()) {
//                detailVO.setAttachment(CopyTools.copy(forumArticleAttachmentList.get(0), ForumArticleAttachmentVo.class));
//            }
//        }

        if (sessionWebUserDto != null) {
            LikeRecord like = likeRecordService.getUserOperRecordByObjectIdAndUserIdAndOpType(articleId, sessionWebUserDto.getUserId(),
                    OperRecordOpTypeEnum.ARTICLE_LIKE.getType());
            if (like != null) {
                detailVO.setHaveLike(true);
            }
            // TODO: 新增收藏操作
            CollectRecord collect = collectRecordService.getUserOperRecordByObjectIdAndUserId(articleId, sessionWebUserDto.getUserId());
            if (collect != null) {
                detailVO.setHaveCollect(true);
            }

        }

//        // 标记文章是否为管理发布的文章
//        UserInfo userInfo = userInfoService.getUserInfoByUserId(forumArticle.getUserId());
//        detailVO.setType(userInfo.getStatus());

        return getSuccessResponseVO(detailVO);
    }

    @RequestMapping("/doLike")
    @GlobalInterceptor(checkLogin = true, checkParams = true, frequencyType = UserOperFrequencyTypeEnum.DO_LIKE)
    public ResponseVO doLike(HttpSession session, @VerifyParam(required = true) String articleId) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        likeRecordService.doLike(articleId, userDto.getUserId(), userDto.getNickName(), OperRecordOpTypeEnum.ARTICLE_LIKE);
        return getSuccessResponseVO(null);
    }

    /**
     * 新增收藏功能。。。
     *
     * @param session
     * @param articleId
     * @return
     */
    @RequestMapping("/doCollect")
    @GlobalInterceptor(checkLogin = true, checkParams = true, frequencyType = UserOperFrequencyTypeEnum.DO_COLLECT)
    public ResponseVO doCollect(HttpSession session, @VerifyParam(required = true) String articleId) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        collectRecordService.doCollect(articleId, userDto.getUserId(), userDto.getNickName());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/getUserDownloadInfo")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVO getUserDownloadInfo(HttpSession session, @VerifyParam(required = true) String fileId) {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(getUserInfoFromSession(session).getUserId());
        Map<String, Object> result = new HashMap();
        result.put("userIntegral", userInfo.getCurrentIntegral());
        ForumArticleAttachmentDownload attachmentDownload = forumArticleAttachmentDownloadService.getForumArticleAttachmentDownloadByFileIdAndUserId(fileId,
                getUserInfoFromSession(session).getUserId());
        result.put("haveDownload", attachmentDownload != null);
        return getSuccessResponseVO(result);
    }

    @RequestMapping("/attachmentDownload")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public void attachmentDownload(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                   @VerifyParam(required = true) String fileId) {
        ForumArticleAttachment attachment = forumArticleAttachmentService.downloadAttachment(fileId, getUserInfoFromSession(session));
        InputStream in = null;
        OutputStream out = null;
        String downloadFileName = attachment.getFileName();
        String filePath = webConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_ATTACHMENT + attachment.getFilePath();
        File file = new File(filePath);
        try {
            in = new FileInputStream(file);
            out = response.getOutputStream();
            response.setContentType("application/x-msdownload; charset=UTF-8");
            // 解决中文文件名乱码问题
            if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") > 0) {//IE浏览器
                downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
            } else {
                downloadFileName = new String(downloadFileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadFileName + "\"");
            byte[] byteData = new byte[1024];
            int len = 0;
            while ((len = in.read(byteData)) != -1) {
                out.write(byteData, 0, len); // write
            }
            out.flush();
        } catch (Exception e) {
            logger.error("下载异常", e);
            throw new BusinessException("下载失败");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                logger.error("IO异常", e);
            }
            try {
                if (out != null) {
                    out.close();
                }

            } catch (IOException e) {
                logger.error("IO异常", e);
            }
        }
    }


    @RequestMapping("/articleDetail4Update")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVO articleDetail4Update(HttpSession session, @VerifyParam(required = true) String articleId) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);

        //更新文章详情校验权限
        checkPermission(userDto.getUserId(), UserPermissionEnum.POST_ARTICLE);

        ForumArticle forumArticle = forumArticleService.getForumArticleByArticleId(articleId);
        if (forumArticle == null || !forumArticle.getUserId().equals(userDto.getUserId())) {
            throw new BusinessException("文章不存在或你无权编辑该文章");
        }
        FormArticleUpdateDetailVO detailVO = new FormArticleUpdateDetailVO();
        detailVO.setForumArticle(forumArticle);
//        if (forumArticle.getAttachmentType() == 1) {
//            ForumArticleAttachmentQuery articleAttachmentQuery = new ForumArticleAttachmentQuery();
//            articleAttachmentQuery.setArticleId(forumArticle.getArticleId());
//            List<ForumArticleAttachment> forumArticleAttachmentList = forumArticleAttachmentService.findListByParam(articleAttachmentQuery);
//            if (!forumArticleAttachmentList.isEmpty()) {
//                detailVO.setAttachment(CopyTools.copy(forumArticleAttachmentList.get(0), ForumArticleAttachmentVo.class));
//            }
//        }
        return getSuccessResponseVO(detailVO);
    }

    @RequestMapping("/updateArticle")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVO updateArticle(HttpSession session,
                                    MultipartFile cover,
                                    MultipartFile attachment,
                                    Integer integral,
                                    @VerifyParam(required = true) String articleId,
                                    @VerifyParam(required = true) Integer pBoardId,
                                    Integer boardId,
                                    @VerifyParam(required = true, max = 150) String title,
                                    @VerifyParam(required = true) String content,
                                    String markdownContent,
                                    @VerifyParam(required = true) Integer editorType,
                                    @VerifyParam(max = 200) String summary,
                                    @VerifyParam(required = true) Integer attachmentType) {
        title = StringTools.escapeTitle(title);
        SessionWebUserDto userDto = getUserInfoFromSession(session);

        // 更新文章需要校验权限
        checkPermission(userDto.getUserId(), UserPermissionEnum.POST_ARTICLE);

        ForumArticle forumArticle = new ForumArticle();
        forumArticle.setArticleId(articleId);
        forumArticle.setpBoardId(pBoardId);
        forumArticle.setBoardId(boardId);
        forumArticle.setTitle(title);
        forumArticle.setContent(content);
        forumArticle.setMarkdownContent(markdownContent);
        forumArticle.setEditorType(editorType);
        forumArticle.setSummary(summary);
        forumArticle.setUserIpAddress(userDto.getProvince());
        forumArticle.setAttachmentType(attachmentType);
        forumArticle.setUserId(userDto.getUserId());
        // 修改之后就得进行新的一轮审核
        forumArticle.setStatus(ArticleStatusEnum.NO_AUDIT.getStatus());
        forumArticle.setStatus2(ArticleStatusEnum.NO_POST.getStatus());
        //附件信息
        ForumArticleAttachment forumArticleAttachment = new ForumArticleAttachment();
        forumArticleAttachment.setIntegral(integral == null ? 0 : integral);

        forumArticleService.updateArticle(userDto.getAdmin(), forumArticle, forumArticleAttachment, cover, attachment);
        return getSuccessResponseVO(forumArticle.getArticleId());
    }

    /**
     * 搜索
     *
     * @param keyword
     * @return
     */
    @RequestMapping("/search")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO search(@VerifyParam(required = true, min = 3) String keyword) {
        ForumArticleQuery query = new ForumArticleQuery();
        query.setTitleFuzzy(keyword);
        query.setStatus2(ArticleStatusEnum.POST.getStatus());
        PaginationResultVO result = forumArticleService.findListByPage(query);
        return getSuccessResponseVO(result);
    }

    @RequestMapping("/deleteArticle")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO deleteArticle(HttpSession session,
                                    @VerifyParam(required = true) String articleId) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (userDto == null) {
            throw new BusinessException("请先登录");
        }

        ForumArticle forumArticle = forumArticleService.getForumArticleByArticleId(articleId);

        if (forumArticle == null) {
            throw new BusinessException("文章不存在，删除失败");
        }

        if (!forumArticle.getUserId().equals(userDto.getUserId())) {
            throw new BusinessException("不是你的文章，删除失败！");
        }

        forumArticle.setStatus(ArticleStatusEnum.DEL.getStatus());
        forumArticle.setStatus2(ArticleStatusEnum.NO_POST.getStatus());
        forumArticleService.updateForumArticleByArticleId(forumArticle, articleId);

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
            throw new BusinessException("不好一下，您没有【" + enumItem.getDesc() + "】的权限！");
        }
    }

}
