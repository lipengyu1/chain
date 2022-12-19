package com.zxn.chain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StoreManagerDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 门店员工编号
     */
    private Long storeEmployeeNumber;
    /**
     * 姓名
     */
    private String storeEmployeeName;
    /**
     * 用户名
     */
    private String username;
    /**
     * 联系方式
     */
    private String storeEmployeeTel;
    /**
     * 门店位置
     */
    private String storeLocation;
    /**
     * 产品销售状况
     */
    private String storeSaleStatus;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 投诉状况
     */
    private String storeComplains;
    /**
     * 角色
     */
    private String role;
    /**
     * 备注
     */
    private String remarks;
    /**
     * token
     */
    private String token;
}
