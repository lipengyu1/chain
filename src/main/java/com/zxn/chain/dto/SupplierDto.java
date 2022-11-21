package com.zxn.chain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupplierDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 名称
     */
    private String supplierName;
    /**
     * 地址
     */
    private String supplierAddress;
    /**
     * 商品类型
     */
    private String shopCategory;
    /**
     * 备注
     */
    private String remarks;
}
