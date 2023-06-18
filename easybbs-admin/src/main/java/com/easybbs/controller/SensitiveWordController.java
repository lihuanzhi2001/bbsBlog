package com.easybbs.controller;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.controller.base.BaseController;
import com.easybbs.entity.po.SensitiveWord;
import com.easybbs.entity.query.SensitiveWordQuery;
import com.easybbs.entity.vo.ResponseVO;
import com.easybbs.service.SensitiveWordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lihuanzhi
 * @since 2023/4/11 9:58
 */
@RestController
@RequestMapping("/sensitive")
public class SensitiveWordController extends BaseController {

    @Resource
    private SensitiveWordService sensitiveWordService;

    // TODO 查询
    @RequestMapping("/loadWord")
    public ResponseVO loadWordList(SensitiveWordQuery query) {
        query.setOrderBy("create_time desc");
        return getSuccessResponseVO(sensitiveWordService.findListByPage(query));
    }

    // TODO 新增
    @RequestMapping("/addWord")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO addWord(@VerifyParam(required = true) String word,
                              Integer status) {

        SensitiveWord bean = new SensitiveWord();
        bean.setWord(word);
        if (status == null) {
            bean.setStatus(0);
        } else {
            bean.setStatus(status);
        }
        bean.setCreateTime(new Date());
        sensitiveWordService.add(bean);

        return getSuccessResponseVO(null);

    }

    // TODO 修改
    @RequestMapping("/editWord")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO editWord(@VerifyParam(required = true) Integer id,
                               @VerifyParam(required = true) String word,
                               Integer status) {
        // 如果该敏感词为已使用状态则无法修改
//        SensitiveWord notice = sensitiveWordService.getSensitiveWordById(id);
//        if (notice.getStatus() == 1){
//            throw new BusinessException("当前敏感词未已使用状态，请撤销使用再操作!");
//        }
        SensitiveWord bean = new SensitiveWord();
        bean.setWord(word);
        if (status == null) {
            bean.setStatus(0);
        } else {
            bean.setStatus(status);
        }
        sensitiveWordService.updateSensitiveWordById(bean, id);

        return getSuccessResponseVO(null);

    }

    // TODO 批量切换状态
    @RequestMapping("/changeStatus")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO changeStatus(@VerifyParam(required = true) String ids,
                                   @VerifyParam(required = true) Integer status) {

        String[] idArray = ids.split(",");
        for (String id : idArray) {
            sensitiveWordService.updateSensitiveWordStatusById(Integer.parseInt(id), status);
        }

        return getSuccessResponseVO(null);
    }


    // TODO 批量删除
    @RequestMapping("/del")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO delWord(@VerifyParam(required = true) String ids) {

        String[] idArray = ids.split(",");
        for (String id : idArray) {
            sensitiveWordService.deleteSensitiveWordById(Integer.parseInt(id));
        }

        return getSuccessResponseVO(null);
    }

}
