package com.easybbs.entity.vo.web;

import com.easybbs.entity.dto.UserPrivacyDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户信息
 */
@Data
public class UserInfoVO implements Serializable {


    /**
     * 用户ID
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户类型(-1管理员)
     */
    private Integer type;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 个人描述
     */
    private String personDescription;

    /**
     * 加入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date joinTime;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastLoginTime;

    /**
     * 当前积分
     */
    private Integer currentIntegral;

    /**
     * 发帖
     */
    public Integer postCount;

    /**
     * 收到的赞
     */
    public Integer likeCount;

    /**
     * 被收藏的文章
     */
    public Integer collectCount;

    /**
     * TODO 关注量
     */
    public Integer followCount;

    /**
     * TODO 粉丝量
     */
    public Integer fansCount;

    /**
     * TODO 新增是否关注标签
     */
    public Boolean haveFollow = false;

    /**
     * 新增博主隐私设置信息
     */
    public UserPrivacyDto userPrivacyDto;


}
