package com.zxn.chain.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class MemberDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 会员卡号
     */
    private Long memberNum;
    /**
     * 会员姓名
     */
    private String memberName;
    /**
     * 会员等级
     */
    private String memberGrade;
    /**
     * 会员性别
     */
    private String memberSex;
    /**
     * 会员状态
     */
    private String memberStatus;
    /**
     * 电话
     */
    private String memberTel;
    /**
     * 备注
     */
    private String remarks;
    /**
     * token
     */
    private String token;
    /**
     * 头像
     */
    private String avatar;
}
