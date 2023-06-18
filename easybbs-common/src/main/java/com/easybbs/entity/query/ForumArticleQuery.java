package com.easybbs.entity.query;

/**
 * 文章信息参数
 */

public class ForumArticleQuery extends BaseParam {

    /**
     * 文章ID
     */
    private String articleId;

    private String articleIdFuzzy;

    /**
     * 板块ID
     */
    private Integer boardId;

    /**
     * 板块名称
     */
    private String boardName;

    private String boardNameFuzzy;

    /**
     * 父级板块ID
     */
    private Integer pBoardId;

    /**
     * 父板块名称
     */
    private String pBoardName;

    private String pBoardNameFuzzy;

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
     * 最后登录ip地址
     */
    private String userIpAddress;

    private String userIpAddressFuzzy;

    /**
     * 标题
     */
    private String title;

    private String titleFuzzy;

    /**
     * 封面
     */
    private String cover;

    private String coverFuzzy;

    /**
     * 内容
     */
    private String content;

    private String contentFuzzy;

    /**
     * markdown内容
     */
    private String markdownContent;

    private String markdownContentFuzzy;

    /**
     * 0:富文本编辑器 1:markdown编辑器
     */
    private Integer editorType;

    /**
     * 摘要
     */
    private String summary;

    private String summaryFuzzy;

    /**
     * 发布时间
     */
    private String postTime;

    private String postTimeStart;

    private String postTimeEnd;

    /**
     * 最后更新时间
     */
    private String lastUpdateTime;

    private String lastUpdateTimeStart;

    private String lastUpdateTimeEnd;

    /**
     * 阅读数量
     */
    private Integer readCount;

    /**
     * 点赞数
     */
    private Integer goodCount;

    /**
     * 收藏数
     */
    private Integer collectCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 0未置顶  1:已置顶
     */
    private Integer topType;

    /**
     * 0:没有附件  1:有附件
     */
    private Integer attachmentType;

    /**
     * -1已删除 0:待审核  1:已审核 4:审核未通过
     */
    private Integer status;

    /**
     * 2:未发布  3:已发布
     */
    private Integer status2;

    /**
     * 评论用户ID
     */
    private String commentUserId;

    /**
     * 点赞用户ID
     */
    private String likeUserId;

    private String keyword;

    /**
     * 收藏用户ID
     */
    private String collectUserId;

    private String collectKeyword;

    /**
     * 当前用户登录ID
     */
    private String currentUserId;

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

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardNameFuzzy() {
        return boardNameFuzzy;
    }

    public void setBoardNameFuzzy(String boardNameFuzzy) {
        this.boardNameFuzzy = boardNameFuzzy;
    }

    public Integer getpBoardId() {
        return pBoardId;
    }

    public void setpBoardId(Integer pBoardId) {
        this.pBoardId = pBoardId;
    }

    public String getpBoardName() {
        return pBoardName;
    }

    public void setpBoardName(String pBoardName) {
        this.pBoardName = pBoardName;
    }

    public String getpBoardNameFuzzy() {
        return pBoardNameFuzzy;
    }

    public void setpBoardNameFuzzy(String pBoardNameFuzzy) {
        this.pBoardNameFuzzy = pBoardNameFuzzy;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleFuzzy() {
        return titleFuzzy;
    }

    public void setTitleFuzzy(String titleFuzzy) {
        this.titleFuzzy = titleFuzzy;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverFuzzy() {
        return coverFuzzy;
    }

    public void setCoverFuzzy(String coverFuzzy) {
        this.coverFuzzy = coverFuzzy;
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

    public String getMarkdownContent() {
        return markdownContent;
    }

    public void setMarkdownContent(String markdownContent) {
        this.markdownContent = markdownContent;
    }

    public String getMarkdownContentFuzzy() {
        return markdownContentFuzzy;
    }

    public void setMarkdownContentFuzzy(String markdownContentFuzzy) {
        this.markdownContentFuzzy = markdownContentFuzzy;
    }

    public Integer getEditorType() {
        return editorType;
    }

    public void setEditorType(Integer editorType) {
        this.editorType = editorType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummaryFuzzy() {
        return summaryFuzzy;
    }

    public void setSummaryFuzzy(String summaryFuzzy) {
        this.summaryFuzzy = summaryFuzzy;
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

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateTimeStart() {
        return lastUpdateTimeStart;
    }

    public void setLastUpdateTimeStart(String lastUpdateTimeStart) {
        this.lastUpdateTimeStart = lastUpdateTimeStart;
    }

    public String getLastUpdateTimeEnd() {
        return lastUpdateTimeEnd;
    }

    public void setLastUpdateTimeEnd(String lastUpdateTimeEnd) {
        this.lastUpdateTimeEnd = lastUpdateTimeEnd;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getTopType() {
        return topType;
    }

    public void setTopType(Integer topType) {
        this.topType = topType;
    }

    public Integer getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(Integer attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus2() {
        return status2;
    }

    public void setStatus2(Integer status2) {
        this.status2 = status2;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getLikeUserId() {
        return likeUserId;
    }

    public void setLikeUserId(String likeUserId) {
        this.likeUserId = likeUserId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCollectUserId() {
        return collectUserId;
    }

    public void setCollectUserId(String collectUserId) {
        this.collectUserId = collectUserId;
    }

    public String getCollectKeyword() {
        return collectKeyword;
    }

    public void setCollectKeyword(String collectKeyword) {
        this.collectKeyword = collectKeyword;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }
}
