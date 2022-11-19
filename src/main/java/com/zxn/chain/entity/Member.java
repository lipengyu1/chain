package com.zxn.chain.entity;

import lombok.Data;

@Data
public class Member {

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
     * 状态 1正常0删除
     */
    private Integer status;
    /**
     * 备注
     */
    private String remarks;
}
