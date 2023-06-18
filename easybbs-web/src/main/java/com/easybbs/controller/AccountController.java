package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.constants.Constants;
import com.easybbs.entity.dto.CreateImageCode;
import com.easybbs.entity.dto.SessionWebUserDto;
import com.easybbs.entity.dto.SysSetting4AuditDto;
import com.easybbs.entity.dto.SysSetting4CommentDto;
import com.easybbs.entity.enums.VerifyRegexEnum;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.entity.vo.web.SysSettingVO;
import com.easybbs.exception.BusinessException;
import com.easybbs.service.EmailCodeService;
import com.easybbs.service.UserInfoService;
import com.easybbs.utils.SysCacheUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class AccountController extends BaseController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private EmailCodeService emailCodeService;

    /**
     * 验证码
     */
    @RequestMapping(value = "/checkCode")
    public void checkCode(HttpServletResponse response, HttpSession session, Integer type) throws
            IOException {
        CreateImageCode vCode = new CreateImageCode(130, 38, 5, 10);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = vCode.getCode();
        if (type == null || type == 0) {
            session.setAttribute(Constants.CHECK_CODE_KEY, code);
        } else {
            session.setAttribute(Constants.CHECK_CODE_KEY_EMAIL, code);
        }
        vCode.write(response.getOutputStream());
    }


    @RequestMapping("/sendEmailCode")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO sendEmailCode(HttpSession session,
                                    @VerifyParam(required = true) String email,
                                    @VerifyParam(required = true) String checkCode,
                                    @VerifyParam(required = true) Integer type) {
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY_EMAIL))) {
                throw new BusinessException("图片验证码不正确");
            }
            emailCodeService.sendEmailCode(email, type);
            return getSuccessResponseVO(null);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY_EMAIL);
        }
    }

    /**
     * 新增的简单发送邮件验证码
     *
     * @param session
     * @param email
     * @param type
     * @return
     */
    @RequestMapping("/sendEmailCode2")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO sendEmailCode2(HttpSession session,
                                     @VerifyParam(required = true) String email,
                                     @VerifyParam(required = true) Integer type) {
        try {
            emailCodeService.sendEmailCode(email, type);
            return getSuccessResponseVO(null);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY_EMAIL);
        }
    }

    // TODO: old register
//    @RequestMapping("/register")
//    @GlobalInterceptor(checkParams = true)
//    public ResponseVO register(HttpSession session,
//                               @VerifyParam(required = true) String email,
//                               @VerifyParam(required = true, max = 20) String nickName,
//                               @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD, min = 8, max = 18) String password,
//                               @VerifyParam(required = true) String checkCode) {
////                               @VerifyParam(required = true) String emailCode
//        try {
//            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
//                throw new BusinessException("图片验证码不正确");
//            }
////            userInfoService.register(email, nickName, password, emailCode);
//            userInfoService.register(email, nickName, password);
//            return getSuccessResponseVO(null);
//        } finally {
//            session.removeAttribute(Constants.CHECK_CODE_KEY);
//        }
//    }

    // TODO: new register
    @RequestMapping("/register")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO register(HttpSession session,
                               @VerifyParam(required = true) String username,
                               @VerifyParam(required = true, max = 20) String nickName,
                               @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD, min = 8, max = 18) String password,
                               @VerifyParam(required = true) String checkCode) {
//                               @VerifyParam(required = true) String emailCode
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码不正确");
            }
//            userInfoService.register(email, nickName, password, emailCode);
            userInfoService.register(username, nickName, password);
            return getSuccessResponseVO(null);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO login(HttpSession session, HttpServletRequest request,
                            @VerifyParam(required = true) String username,
                            @VerifyParam(required = true) String password,
                            @VerifyParam(required = true) String checkCode) {
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码不正确");
            }
            // 校验用户名
            SessionWebUserDto sessionWebUserDto = userInfoService.login(username, password, getIpAddr(request));
            session.setAttribute(Constants.SESSION_KEY, sessionWebUserDto);
            return getSuccessResponseVO(sessionWebUserDto);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

    /*TODO: 根据邮箱修改密码*/
    @RequestMapping("/resetPwdByEmail")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO resetPwdByEmail(HttpSession session,
                                      String email,
                                      @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD, min = 8, max = 18) String password,
                                      @VerifyParam(required = true) String checkCode,
                                      @VerifyParam(required = true) String emailCode) {
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码不正确");
            }
            userInfoService.resetPwdByEmail(email, password, emailCode);
            return getSuccessResponseVO(null);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

    /*TODO: 根据旧密码更新密码*/
    @RequestMapping("/resetPwdByOldPwd")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO resetPwdByOldPwd(HttpSession session,
                                       String username,
                                       @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD, min = 8, max = 18) String oldPassword,
                                       @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD, min = 8, max = 18) String newPassword) {
        userInfoService.resetPwdByOldPwd(username, oldPassword, newPassword);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/getUserInfo")
    @GlobalInterceptor
    public ResponseVO getUserInfo(HttpSession session) {
        SessionWebUserDto sessionWebUserDto = getUserInfoFromSession(session);
        return getSuccessResponseVO(sessionWebUserDto);
    }

    @RequestMapping("/logout")
    public ResponseVO logout(HttpSession session) {
        session.invalidate();
        return getSuccessResponseVO(null);
    }


    @RequestMapping("/getSysSetting")
    @GlobalInterceptor()
    public ResponseVO getSysSetting() {
        SysSetting4CommentDto commentDto = SysCacheUtils.getSysSetting().getCommentSetting();
        SysSetting4AuditDto auditStting = SysCacheUtils.getSysSetting().getAuditStting();

        Boolean commentOpen = commentDto == null ? true : commentDto.getCommentOpen();
        Boolean noAuditPostOpen = auditStting == null ? true : auditStting.getPostAudit();

        SysSettingVO sysSettingVO = new SysSettingVO();
        sysSettingVO.setCommentOpen(commentOpen);
        sysSettingVO.setNoAuditPost(noAuditPostOpen);
        return getSuccessResponseVO(sysSettingVO);
    }


}
