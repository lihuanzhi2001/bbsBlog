package com.easybbs.service.impl;

/**
 * @author lihuanzhi
 * @since 2023/3/28 14:54
 */

import com.easybbs.entity.enums.MessageStatusEnum;
import com.easybbs.entity.enums.MessageTypeEnum;
import com.easybbs.entity.enums.PageSize;
import com.easybbs.entity.enums.UpdateArticleCountTypeEnum;
import com.easybbs.entity.po.CollectRecord;
import com.easybbs.entity.po.ForumArticle;
import com.easybbs.entity.po.ForumComment;
import com.easybbs.entity.po.UserMessage;
import com.easybbs.entity.query.*;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.exception.BusinessException;
import com.easybbs.mappers.CollectRecordMapper;
import com.easybbs.mappers.ForumArticleMapper;
import com.easybbs.mappers.ForumCommentMapper;
import com.easybbs.mappers.UserMessageMapper;
import com.easybbs.service.CollectRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 收藏
 */
@Service("collectRecordService")
public class CollectRecordServiceImpl implements CollectRecordService {

    @Resource
    private CollectRecordMapper<CollectRecord, CollectRecordQuery> collectRecordMapper;

    @Resource
    private ForumArticleMapper<ForumArticle, ForumArticleQuery> forumArticleMapper;

    @Resource
    private ForumCommentMapper<ForumComment, ForumCommentQuery> forumCommentMapper;

    @Resource
    private UserMessageMapper<UserMessage, UserMessageQuery> userMessageMapper;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<CollectRecord> findListByParam(CollectRecordQuery param) {
        return this.collectRecordMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(CollectRecordQuery param) {
        return this.collectRecordMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<CollectRecord> findListByPage(CollectRecordQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<CollectRecord> list = this.findListByParam(param);
        PaginationResultVO<CollectRecord> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(CollectRecord bean) {
        return this.collectRecordMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<CollectRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.collectRecordMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<CollectRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.collectRecordMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据OpId获取对象
     */
    @Override
    public CollectRecord getUserOperRecordByOpId(Integer opId) {
        return this.collectRecordMapper.selectByOpId(opId);
    }

    /**
     * 根据OpId修改
     */
    @Override
    public Integer updateUserOperRecordByOpId(CollectRecord bean, Integer opId) {
        return this.collectRecordMapper.updateByOpId(bean, opId);
    }

    /**
     * 根据OpId删除
     */
    @Override
    public Integer deleteUserOperRecordByOpId(Integer opId) {
        return this.collectRecordMapper.deleteByOpId(opId);
    }

    /**
     * 根据ObjectIdAndUserId获取对象
     */
    @Override
    public CollectRecord getUserOperRecordByObjectIdAndUserId(String objectId, String userId) {
        return this.collectRecordMapper.selectByObjectIdAndUserId(objectId, userId);
    }

    /**
     * 根据ObjectIdAndUserId修改
     */
    @Override
    public Integer updateUserOperRecordByObjectIdAndUserId(CollectRecord bean, String objectId, String userId) {
        return this.collectRecordMapper.updateByObjectIdAndUserId(bean, objectId, userId);
    }

    /**
     * 根据ObjectIdAndUserId删除
     */
    @Override
    public Integer deleteUserOperRecordByObjectIdAndUserId(String objectId, String userId) {
        return this.collectRecordMapper.deleteByObjectIdAndUserId(objectId, userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void doCollect(String objectId, String userId, String nickName) {
        UserMessage userMessage = new UserMessage();
        userMessage.setCreateTime(new Date());
        CollectRecord collectRecord = null;

        collectRecord = articleCollect(objectId, userId);

        ForumArticle forumArticle = forumArticleMapper.selectByArticleId(objectId);
        userMessage.setArticleId(objectId);
        userMessage.setArticleTitle(forumArticle.getTitle());
        userMessage.setMessageType(MessageTypeEnum.ARTICLE_COLLECT.getType());
        userMessage.setCommentId(0);
        userMessage.setReceivedUserId(forumArticle.getUserId());

        userMessage.setSendUserId(userId);
        userMessage.setSendNickName(nickName);
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        if (collectRecord == null && !userId.equals(userMessage.getReceivedUserId())) {
            userMessageMapper.insert(userMessage);
        }
    }


    /**
     * 文章收藏，取消收藏
     *
     * @param objectId
     * @param userId
     */
    public CollectRecord articleCollect(String objectId, String userId) {
        CollectRecord record = this.collectRecordMapper.selectByObjectIdAndUserId(objectId, userId);
        if (record != null) {
            this.collectRecordMapper.deleteByObjectIdAndUserId(objectId, userId);
            forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.COLLECT_COUNT.getType(), -1, objectId);
        } else {
            ForumArticle forumArticle = forumArticleMapper.selectByArticleId(objectId);
            if (null == forumArticle) {
                throw new BusinessException("文章不存在");
            }
            CollectRecord operRecord = new CollectRecord();
            operRecord.setObjectId(objectId);
            operRecord.setUserId(userId);
            operRecord.setCreateTime(new Date());
            operRecord.setAuthorUserId(forumArticle.getUserId());
            this.collectRecordMapper.insert(operRecord);
            forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.COLLECT_COUNT.getType(), 1, objectId);
        }
        return record;
    }

}
