package com.zxn.chain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 商品编号
     */
    private Long shopId;
    /**
     * 商品分类
     */
    private String shopCategory;
    /**
     * 供应商
     */
    private String shopSupplier;
    /**
     * 存放位置
     */
    private String shopBase;
    /**
     * 进货数量
     */
    private Integer startNum;
    /**
     * 销售数量
     */
    private Integer saleNum;
    /**
     * 安全数量
     */
    private Integer safetyStock;
    /**
     * 备注
     */
    private String remarks;
}
