package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.constants.Constants;
import com.easybbs.entity.enums.ResponseCodeEnum;
import com.easybbs.entity.po.ComplaintHistoriesRecord;
import com.easybbs.entity.query.ComplaintHistoriesRecordQuery;
import com.easybbs.entity.query.ComplaintRecordQuery;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.service.ComplaintHistoriesRecordService;
import com.easybbs.service.ComplaintRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/complaint")
public class ComplaintController extends BaseController {

    @Resource
    private ComplaintRecordService complaintRecordService;

    @Resource
    private ComplaintHistoriesRecordService complaintHistoriesRecordService;

    @RequestMapping("/loadComplaintList")
    public ResponseVO loadUserList(ComplaintRecordQuery complaintRecordQuery) {
        complaintRecordQuery.setOrderBy("create_time desc");
        return getSuccessResponseVO(complaintRecordService.findListByPage(complaintRecordQuery));
    }

    @RequestMapping("/doDeal")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO doDeal(@VerifyParam(required = true) Integer status,
                             @VerifyParam(required = true) Integer complaintId,
                             String comment) {

        // 没有内容就插入了，直接更改投诉状态
        if (comment != null || comment.length() > 0) {

            // 插入投诉
            ComplaintHistoriesRecord bean = new ComplaintHistoriesRecord();
            bean.setAdminId("0");
            bean.setComment(comment);
            bean.setComplaintId(complaintId);
            bean.setUpdateTime(new Date());

            complaintHistoriesRecordService.add(bean);
        }

        // 更新投诉的状态
        complaintRecordService.updateComplaintStatusById(complaintId, status);

        return getSuccessResponseVO(null);
    }

    @RequestMapping("/search")
    public ResponseVO getComment(@VerifyParam(required = true) Integer complaintId) {
        ComplaintHistoriesRecordQuery query = new ComplaintHistoriesRecordQuery();
        query.setComplaintId(complaintId);

        List<ComplaintHistoriesRecord> list = complaintHistoriesRecordService.findListByParam(query);

        if (list.size() > 0) {
            return getSuccessResponseVO(list.get(0));
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
