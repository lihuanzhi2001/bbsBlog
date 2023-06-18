package com.easybbs.controller;

import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.dto.StatInfo1;
import com.easybbs.entity.dto.StatInfo2;
import com.easybbs.entity.dto.StatInfo4;
import com.easybbs.entity.dto.StatInfo4Item;
import com.easybbs.entity.enums.ArticleStatusEnum;
import com.easybbs.entity.enums.ComplaintEnum;
import com.easybbs.entity.query.*;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.service.*;
import com.easybbs.utils.DateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stat")
public class IndexController extends BaseController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ForumArticleService forumArticleService;
    @Resource
    private LikeRecordService likeRecordService;
    @Resource
    private CollectRecordService collectRecordService;
    @Resource
    private ComplaintRecordService complaintRecordService;

    // 基础数据
    @RequestMapping("/getStatInfo1")
    public ResponseVO getStatInfo1() {
        List<StatInfo1> result = new ArrayList<>();

        // TODO: 统计每周新增用户
        // 7天
        UserInfoQuery userQuery = new UserInfoQuery();
        userQuery.setLastLoginTimeStart(DateUtil.getPre7Day());
        userQuery.setLastLoginTimeEnd(DateUtil.getNowDay());
        Integer user7Count = userInfoService.findCountByParam(userQuery);
        // 总
        userQuery = new UserInfoQuery();
        // 每周活跃用户
        Integer userCount = userInfoService.findCountByParam(userQuery);

        StatInfo1 statInfo = new StatInfo1();
        statInfo.setTitle("近期活跃用户");
        statInfo.setUnitColor("success");
        statInfo.setUnit("周");
        statInfo.setSubTitle("总用户");
        statInfo.setSubUnit("");
        statInfo.setValue(user7Count);
        statInfo.setSubValue(userCount);

        result.add(statInfo);

        //TODO 每周新增博文
        // 7天
        ForumArticleQuery forumQuery = new ForumArticleQuery();
        forumQuery.setPostTimeStart(DateUtil.getPre7Day());
        forumQuery.setPostTimeEnd(DateUtil.getNowDay());
        // 每周新增博文
        Integer forum7Count = forumArticleService.findCountByParam(forumQuery);
        // 总
        forumQuery = new ForumArticleQuery();
        Integer forumCount = forumArticleService.findCountByParam(forumQuery);

        statInfo = new StatInfo1();
        statInfo.setTitle("新增博文");
        statInfo.setUnitColor("success");
        statInfo.setUnit("周");
        statInfo.setSubTitle("总博文");
        statInfo.setSubUnit("");
        statInfo.setValue(forum7Count);
        statInfo.setSubValue(forumCount);

        result.add(statInfo);

        //TODO 每周新增点赞
        // 7天
        LikeRecordQuery likeQuery = new LikeRecordQuery();
        likeQuery.setCreateTimeStart(DateUtil.getPre7Day());
        likeQuery.setCreateTimeEnd(DateUtil.getNowDay());
        // 每周新增点赞
        Integer like7Count = likeRecordService.findCountByParam(likeQuery);
        // 总
        likeQuery = new LikeRecordQuery();
        Integer likeCount = likeRecordService.findCountByParam(likeQuery);

        statInfo = new StatInfo1();
        statInfo.setTitle("新增点赞");
        statInfo.setUnitColor("success");
        statInfo.setUnit("周");
        statInfo.setSubTitle("总点赞数");
        statInfo.setSubUnit("");
        statInfo.setValue(like7Count);
        statInfo.setSubValue(likeCount);

        result.add(statInfo);

        //TODO 每周新增收藏
        // 7天
        CollectRecordQuery collectQuery = new CollectRecordQuery();
        collectQuery.setCreateTimeStart(DateUtil.getPre7Day());
        collectQuery.setCreateTimeEnd(DateUtil.getNowDay());
        // 每周新增收藏
        Integer collect7Count = collectRecordService.findCountByParam(collectQuery);
        // 总
        collectQuery = new CollectRecordQuery();
        Integer collectCount = collectRecordService.findCountByParam(collectQuery);

        statInfo = new StatInfo1();
        statInfo.setTitle("新增收藏");
        statInfo.setUnitColor("success");
        statInfo.setUnit("周");
        statInfo.setSubTitle("总收藏数");
        statInfo.setSubUnit("");
        statInfo.setValue(collect7Count);
        statInfo.setSubValue(collectCount);

        result.add(statInfo);

        return getSuccessResponseVO(result);

    }

    // 博文
    @RequestMapping("/getStatInfo2")
    public ResponseVO getStatInfo2() {
        // 获取近一个月的日期（“yyyy-MM-dd”）
        List<String> pre30DayList = DateUtil.getPre30DayList();
        // 拿到x轴
        List<String> x = pre30DayList.stream()
                .map(date -> {
                    int i = date.indexOf("-");
                    return date.substring(i + 1);
                })
                .collect(Collectors.toList());

        // 拿到y轴数据
        List<Integer> y = new ArrayList<>();
        ForumArticleQuery query;
        for (String date : pre30DayList) {
            query = new ForumArticleQuery();
            query.setPostTimeStart(date + " 00:00:00");
            query.setPostTimeEnd(date + " 23:59:59");
            Integer count = forumArticleService.findCountByParam(query);
            y.add(count);
        }

        StatInfo2 statInfo = new StatInfo2();
        statInfo.setTitle("近期博文数据");
        statInfo.setUnit("月");
        statInfo.setUnitColor("danger");
        statInfo.setX(x);
        statInfo.setY(y);
        return getSuccessResponseVO(statInfo);

    }

    // 用户（半个月）
    @RequestMapping("/getStatInfo3")
    public ResponseVO getStatInfo3() {
        List<String> pre15DayList = DateUtil.getPre15DayList();
        // 拿到x轴
        List<String> x = pre15DayList.stream()
                .map(date -> {
                    int i = date.indexOf("-");
                    return date.substring(i + 1);
                })
                .collect(Collectors.toList());
        // 拿到y轴数据
        List<Integer> y = new ArrayList<>();
        UserInfoQuery query;
        for (String date : pre15DayList) {
            query = new UserInfoQuery();
            query.setJoinTimeStart(date + " 00:00:00");
            query.setJoinTimeEnd(date + " 23:59:59");
            Integer count = userInfoService.findCountByParam(query);
            y.add(count);
        }

        StatInfo2 statInfo = new StatInfo2();
        statInfo.setTitle("近期注册用户");
        statInfo.setUnit("半个月");
        statInfo.setUnitColor("warning");
        statInfo.setX(x);
        statInfo.setY(y);
        return getSuccessResponseVO(statInfo);

    }

    // 博文和投诉
    @RequestMapping("/getStatInfo4")
    public ResponseVO getStatInfo4() {
        StatInfo4 statInfo4 = new StatInfo4();
        List<StatInfo4Item> bowens = new ArrayList<>();
        // 博文
        // 审核未通过
//        bowens.add(getArticleByStatus(ArticleStatusEnum.ERROR_AUDIT));
        //未审核
        bowens.add(getArticleByStatus(ArticleStatusEnum.NO_AUDIT));
        // 已审核
        bowens.add(getArticleByStatus(ArticleStatusEnum.AUDIT));
        // 未发布
        bowens.add(getArticleByStatus2(ArticleStatusEnum.NO_POST));
        // 已发布
        bowens.add(getArticleByStatus2(ArticleStatusEnum.POST));
        statInfo4.setBowens(bowens);

        // 投诉
        List<StatInfo4Item> complaints = new ArrayList<>();
        //未处理
        complaints.add(getComplaintByStatus(ComplaintEnum.NO_HANDLE));
        // 处理中
        complaints.add(getComplaintByStatus(ComplaintEnum.HANDLEING));
        // 已处理
        complaints.add(getComplaintByStatus(ComplaintEnum.OK_HANDLE));
        // 已关闭
        complaints.add(getComplaintByStatus(ComplaintEnum.CLOSE));
        statInfo4.setComplaints(complaints);

        return getSuccessResponseVO(statInfo4);
    }

    //博文
    public StatInfo4Item getArticleByStatus(ArticleStatusEnum articleEnum) {
        ForumArticleQuery query = new ForumArticleQuery();
        // 审核未通过
        query.setStatus(articleEnum.getStatus());
        Integer count = forumArticleService.findCountByParam(query);
        StatInfo4Item statInfo4Item = new StatInfo4Item();
        statInfo4Item.setLabel(articleEnum.getDesc());
        statInfo4Item.setValue(count);

        return statInfo4Item;
    }

    public StatInfo4Item getArticleByStatus2(ArticleStatusEnum articleEnum) {
        ForumArticleQuery query = new ForumArticleQuery();
        // 审核未通过
        query.setStatus2(articleEnum.getStatus());
        Integer count = forumArticleService.findCountByParam(query);
        StatInfo4Item statInfo4Item = new StatInfo4Item();
        statInfo4Item.setLabel(articleEnum.getDesc());
        statInfo4Item.setValue(count);

        return statInfo4Item;
    }

    //投诉
    public StatInfo4Item getComplaintByStatus(ComplaintEnum complaintEnum) {
        ComplaintRecordQuery query = new ComplaintRecordQuery();
        // 审核未通过
        query.setStatus(complaintEnum.getStatus());
        Integer count = complaintRecordService.findCountByParam(query);
        StatInfo4Item statInfo4Item = new StatInfo4Item();
        statInfo4Item.setLabel(complaintEnum.getDesc());
        statInfo4Item.setValue(count);

        return statInfo4Item;
    }


}
