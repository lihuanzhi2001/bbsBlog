package com.easybbs.controller;

import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.dto.SessionWebUserDto;
import com.easybbs.entity.enums.ArticleStatusEnum;
import com.easybbs.entity.po.ForumArticle;
import com.easybbs.entity.po.UserInfo;
import com.easybbs.entity.query.ForumArticleQuery;
import com.easybbs.entity.vo.PaginationResultVO;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.entity.vo.web.ForumArticleVO;
import com.easybbs.service.ForumArticleService;
import com.easybbs.service.UserInfoService;
import com.easybbs.utils.CopyTools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Resource
    private ForumArticleService forumArticleService;

    @Resource
    private UserInfoService userInfoService;

    // 首页推荐 recommend

    /**
     * 暂定是这样（如果有空的话回来写）
     * 15篇文章
     * 首页推荐策略
     * (每一个月为一个区间查询)
     * 评论(5) > 收藏(3) > 点赞(2) > 阅读量(1)
     * 未登录：15篇文章
     * 已登录: 10篇已关注的博主的文章、5篇未关注的博主的文章
     */
    @RequestMapping("/recommend")
    public ResponseVO recommend(HttpSession session) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);

        ForumArticleQuery query = new ForumArticleQuery();
        query.setOrderBy("last_update_time desc");
        query.setStatus2(ArticleStatusEnum.POST.getStatus());

        List<ForumArticleVO> result = new ArrayList<>();

        // 实现key的降序
        Map<Integer, List<ForumArticleVO>> map = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        List<ForumArticle> faList = forumArticleService.findListByParam(query);
        if (faList != null && faList.size() > 0) {
            List<ForumArticleVO> forumArticleVOS = CopyTools.copyList(faList, ForumArticleVO.class);
            for (ForumArticleVO forumArticleVO : forumArticleVOS) {
                UserInfo userInfo = userInfoService.getUserInfoByUserId(forumArticleVO.getUserId());
                forumArticleVO.setType(userInfo.getStatus());
            }
            // 打分排序
            for (ForumArticleVO forumArticleVO : forumArticleVOS) {
                Integer score = getScore(forumArticleVO);
                if (map.get(score) == null) {
                    map.put(score, new ArrayList<>());
                }

                List<ForumArticleVO> favList = map.get(score);
                favList.add(forumArticleVO);
            }

            // 最后提前前15
            int maxCount = 15;
            for (Map.Entry<Integer, List<ForumArticleVO>> entry : map.entrySet()) {
                if (maxCount <= 0) {
                    break;
                }
                List<ForumArticleVO> list = entry.getValue();
                if (list.size() > 0) {
                    // 小于maxCount 就全部添加
                    if (list.size() < maxCount) {
                        result.addAll(list);
                        maxCount -= list.size();
                    } else {
                        List<ForumArticleVO> newList = list.subList(0, maxCount);
                        result.addAll(newList);
                    }
                }
            }
        }

        PaginationResultVO<ForumArticleVO> prv = new PaginationResultVO<>(15, 15, 0, 15, result);
        return getSuccessResponseVO(prv);
    }

    // 计算文章的分数
    private Integer getScore(ForumArticleVO forumArticleVO) {
        Integer score = 0;
        // 评论
        score += forumArticleVO.getCommentCount() * 5;
        // 收藏
        score += forumArticleVO.getCollectCount() * 3;
        // 点赞
        score += forumArticleVO.getGoodCount() * 2;
        // 阅读量
        score += forumArticleVO.getReadCount() * 1;

        return score;

    }
}
