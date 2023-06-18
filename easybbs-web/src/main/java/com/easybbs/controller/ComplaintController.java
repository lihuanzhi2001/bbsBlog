package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.constants.Constants;
import com.easybbs.entity.dto.SessionWebUserDto;
import com.easybbs.entity.enums.ResponseCodeEnum;
import com.easybbs.entity.po.ComplaintRecord;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.service.ComplaintRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 投诉接口
 *
 * @author lihuanzhi
 * @since 2023/3/31 11:48
 */
@RestController
@RequestMapping("/complaint")
public class ComplaintController extends BaseController {

    @Resource
    private ComplaintRecordService complaintRecordService;


    @RequestMapping(value = "/push")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVO pushComplaint(HttpSession session,
                                    @VerifyParam(required = true) String objectId,
                                    @VerifyParam(required = true) Integer type,
                                    @VerifyParam(required = true) String content) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);

        ComplaintRecord bean = new ComplaintRecord();
        bean.setUserId(userDto.getUserId());
        bean.setObjectId(objectId);
        bean.setType(type);
        bean.setContent(content);
        bean.setCreateTime(new Date());
        // TODO: 这里就暂时写死，按理来说要写一个枚举类
        bean.setStatus(0);

        complaintRecordService.add(bean);
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

    protected SessionWebUserDto getUserInfoFromSession(HttpSession session) {
        SessionWebUserDto sessionWebUserDto = (SessionWebUserDto) session.getAttribute(Constants.SESSION_KEY);
        return sessionWebUserDto;
    }
}
