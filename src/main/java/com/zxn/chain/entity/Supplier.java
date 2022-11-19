package com.zxn.chain.entity;

import lombok.Data;

@Data
public class Supplier {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 名称
     */
    private String supplierName;
    /**
     * 地址
     */
    private String sipplierAddress;
    /**
     * 商品类型
     */
    private String shopCategory;
    /**
     * 状态1正常0删除
     */
    private Integer status;
    /**
     * 备注
     */
    private String remarks;
}
