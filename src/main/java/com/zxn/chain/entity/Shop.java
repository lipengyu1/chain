package com.zxn.chain.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Shop {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     *商品名称
     */
    private String shopName;
    /**
     * 商品编号
     */
    private Long shopNum;
    /**
     * 图片
     */
    private String picture;
    /**
     * 供应商id
     */
    private Long shopSupplierId;
    /**
     * 存放位置
     */
    private String shopBase;
    /**
     * 进货价
     */
    private BigDecimal restockingPrice;
    /**
     * 售价
     */
    private BigDecimal sellPrice;
    /**
     * 剩余数量
     */
    private Integer quantity;
    /**
     * 商品分类
     */
    private String category;
    /**
     * 1正常0删除
     */
    private Integer status;
    /**
     * 备注
     */
    private String remarks;
    private String shopColor;
    private String shopTag;
    private String shopSubname;
}
