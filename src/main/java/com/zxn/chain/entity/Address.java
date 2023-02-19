package com.zxn.chain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    private Long id;
    /**
     * 会员号
     */
    private Long memberNum;
    /**
     * 地址
     */
    private String address;
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 状态
     */
    private Integer status;
}
