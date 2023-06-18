package com.easybbs.controller;

import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.constants.Constants;
import com.easybbs.entity.enums.ResponseCodeEnum;
import com.easybbs.entity.po.Notice;
import com.easybbs.entity.query.NoticeQuery;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.service.NoticeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/6 18:14
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    @Resource
    private NoticeService noticeService;

    @RequestMapping(value = "/loadNotice")
    public ResponseVO loadNotice() {
        NoticeQuery query = new NoticeQuery();
        // 查询状态为已发布的的公告
        query.setStatus(1);

        List<Notice> result = noticeService.findListByParam(query);
        return getSuccessResponseVO(result);
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
