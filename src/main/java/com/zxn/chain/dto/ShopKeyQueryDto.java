package com.zxn.chain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopKeyQueryDto {
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
     * 售价
     */
    private BigDecimal sellPrice;
    /**
     * 商品分类
     */
    private String category;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 点赞量
     */
    private Integer likeCount;
    private String shopColor;
    private String shopTag;
    private String shopSubname;
}
