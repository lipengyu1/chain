package com.zxn.chain.entity;

import lombok.Data;

@Data
public class StoreManager {
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
     * 密码
     */
    private String password;
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
     * 投诉状况
     */
    private String storeComplains;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态1正常0删除
     */
    private Integer status;
    /**
     * 角色
     */
    private String role;
    /**
     * 备注
     */
    private String remarks;
}
