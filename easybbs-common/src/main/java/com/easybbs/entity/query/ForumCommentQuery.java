package com.easybbs.entity.query;

/**
 * 评论参数
 */

public class ForumCommentQuery extends BaseParam {


    /**
     * 评论ID
     */
    private Integer commentId;

    /**
     * 父级评论ID
     */
    private Integer pCommentId;

    /**
     * 文章ID
     */
    private String articleId;

    private String articleIdFuzzy;

    /**
     * 原来的评论（未屏蔽）
     */
    private String originalContent;

    private String originalContentFuzzy;

    /**
     * 评论内容(屏蔽词过滤后)
     */
    private String content;

    private String contentFuzzy;

    /**
     * 图片
     */
    private String imgPath;

    private String imgPathFuzzy;

    /**
     * 用户ID
     */
    private String userId;

    private String userIdFuzzy;

    /**
     * 昵称
     */
    private String nickName;

    private String nickNameFuzzy;

    /**
     * 用户ip地址
     */
    private String userIpAddress;

    private String userIpAddressFuzzy;

    /**
     * 回复人ID
     */
    private String replyUserId;

    private String replyUserIdFuzzy;

    /**
     * 回复人昵称
     */
    private String replyNickName;

    private String replyNickNameFuzzy;

    /**
     * 0:未置顶  1:置顶
     */
    private Integer topType;

    /**
     * 发布时间
     */
    private String postTime;

    private String postTimeStart;

    private String postTimeEnd;

    /**
     * good数量
     */
    private Integer goodCount;

    /**
     * 0:待审核  1:已审核
     */
    private Integer status;


    /**
     * 查询子评论
     */
    private Boolean loadChildren;

    /**
     * 查询用户是否点赞
     */
    private Boolean queryLikeType;

    /**
     * 当前用户登录ID
     */
    private String currentUserId;

    /**
     * 只查询子评论
     */
    private Boolean onlyQueryChildren;


    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getpCommentId() {
        return pCommentId;
    }

    public void setpCommentId(Integer pCommentId) {
        this.pCommentId = pCommentId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleIdFuzzy() {
        return articleIdFuzzy;
    }

    public void setArticleIdFuzzy(String articleIdFuzzy) {
        this.articleIdFuzzy = articleIdFuzzy;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public String getOriginalContentFuzzy() {
        return originalContentFuzzy;
    }

    public void setOriginalContentFuzzy(String originalContentFuzzy) {
        this.originalContentFuzzy = originalContentFuzzy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentFuzzy() {
        return contentFuzzy;
    }

    public void setContentFuzzy(String contentFuzzy) {
        this.contentFuzzy = contentFuzzy;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPathFuzzy() {
        return imgPathFuzzy;
    }

    public void setImgPathFuzzy(String imgPathFuzzy) {
        this.imgPathFuzzy = imgPathFuzzy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIdFuzzy() {
        return userIdFuzzy;
    }

    public void setUserIdFuzzy(String userIdFuzzy) {
        this.userIdFuzzy = userIdFuzzy;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickNameFuzzy() {
        return nickNameFuzzy;
    }

    public void setNickNameFuzzy(String nickNameFuzzy) {
        this.nickNameFuzzy = nickNameFuzzy;
    }

    public String getUserIpAddress() {
        return userIpAddress;
    }

    public void setUserIpAddress(String userIpAddress) {
        this.userIpAddress = userIpAddress;
    }

    public String getUserIpAddressFuzzy() {
        return userIpAddressFuzzy;
    }

    public void setUserIpAddressFuzzy(String userIpAddressFuzzy) {
        this.userIpAddressFuzzy = userIpAddressFuzzy;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyUserIdFuzzy() {
        return replyUserIdFuzzy;
    }

    public void setReplyUserIdFuzzy(String replyUserIdFuzzy) {
        this.replyUserIdFuzzy = replyUserIdFuzzy;
    }

    public String getReplyNickName() {
        return replyNickName;
    }

    public void setReplyNickName(String replyNickName) {
        this.replyNickName = replyNickName;
    }

    public String getReplyNickNameFuzzy() {
        return replyNickNameFuzzy;
    }

    public void setReplyNickNameFuzzy(String replyNickNameFuzzy) {
        this.replyNickNameFuzzy = replyNickNameFuzzy;
    }

    public Integer getTopType() {
        return topType;
    }

    public void setTopType(Integer topType) {
        this.topType = topType;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostTimeStart() {
        return postTimeStart;
    }

    public void setPostTimeStart(String postTimeStart) {
        this.postTimeStart = postTimeStart;
    }

    public String getPostTimeEnd() {
        return postTimeEnd;
    }

    public void setPostTimeEnd(String postTimeEnd) {
        this.postTimeEnd = postTimeEnd;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getLoadChildren() {
        return loadChildren;
    }

    public void setLoadChildren(Boolean loadChildren) {
        this.loadChildren = loadChildren;
    }

    public Boolean getQueryLikeType() {
        return queryLikeType;
    }

    public void setQueryLikeType(Boolean queryLikeType) {
        this.queryLikeType = queryLikeType;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public Boolean getOnlyQueryChildren() {
        return onlyQueryChildren;
    }

    public void setOnlyQueryChildren(Boolean onlyQueryChildren) {
        this.onlyQueryChildren = onlyQueryChildren;
    }
}
