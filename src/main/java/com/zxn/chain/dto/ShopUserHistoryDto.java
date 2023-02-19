package com.zxn.chain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShopUserHistoryDto implements Serializable {
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
     * 供应商
     */
    private String shopSupplier;
    /**
     * 供应商id
     */
    private Long shopSupplierId;
    /**
     * 存放位置
     */
    private String shopBase;
    /**
     * 售价
     */
    private BigDecimal sellPrice;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 商品分类
     */
    private String category;
    /**
     * 点赞量
     */
    private Integer likeCount;
    /**
     * 查看时间
     */
    private String readTime;
}
