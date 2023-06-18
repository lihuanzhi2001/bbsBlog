package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.constants.Constants;
import com.easybbs.entity.enums.ResponseCodeEnum;
import com.easybbs.entity.po.Notice;
import com.easybbs.entity.query.NoticeQuery;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.exception.BusinessException;
import com.easybbs.service.NoticeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lihuanzhi
 * @since 2023/4/6 11:53
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    @Resource
    private NoticeService noticeService;

    // 查询
    @RequestMapping("/loadNotice")
    public ResponseVO loadNoticeList(NoticeQuery noticeQuery) {
        noticeQuery.setOrderBy("create_time desc");
        return getSuccessResponseVO(noticeService.findListByPage(noticeQuery));
    }

    // 新增
    @RequestMapping("/add")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO addNotice(@VerifyParam(required = true) String title,
                                @VerifyParam(required = true) String content) {

        Notice bean = new Notice();
        bean.setTitle(title);
        bean.setContent(content);
        bean.setStatus(0);
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());

        noticeService.add(bean);

        return getSuccessResponseVO(null);

    }

    // 修改
    @RequestMapping("/edit")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO editNotice(@VerifyParam(required = true) Integer id,
                                 @VerifyParam(required = true) String title,
                                 @VerifyParam(required = true) String content) {
        // 如果该公告为已发布状态则无法修改
        Notice notice = noticeService.getNoticeById(id);
        if (notice.getStatus() == 1) {
            throw new BusinessException("当前公告为已发布状态，请撤回后再操作!");
        }
        Notice bean = new Notice();
        bean.setTitle(title);
        bean.setContent(content);
        bean.setUpdateTime(new Date());
        noticeService.updateNoticeById(bean, id);

        return getSuccessResponseVO(null);

    }

    // 批量切换状态
    @RequestMapping("/changeStatus")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO changeStatus(@VerifyParam(required = true) String ids,
                                   @VerifyParam(required = true) Integer status) {

        String[] idArray = ids.split(",");
        for (String id : idArray) {
            noticeService.updateNoticeStatusById(Integer.parseInt(id), status);
        }

        return getSuccessResponseVO(null);
    }

    // 批量删除
    @RequestMapping("/del")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO delNotice(@VerifyParam(required = true) String ids) {

        String[] idArray = ids.split(",");
        for (String id : idArray) {
            noticeService.deleteNoticeById(Integer.parseInt(id));
        }

        return getSuccessResponseVO(null);
    }


    protected <T> ResponseVO getSuccessResponseVO(T t) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(Constants.STATUC_SUCCESS);
        responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        responseVO.setData(t);
        return responseVO;
    }


}
