package com.zxn.chain.entity;

import lombok.Data;

@Data
public class ShopLikeCount {
    /**
     * 商品id
     */
    private Long shopId;
    /**
     * 点赞数量统计
     */
    private Integer shopLikeCount;

    public ShopLikeCount(Long shopId, Integer shopLikeCount) {
        this.shopId = shopId;
        this.shopLikeCount = shopLikeCount;
    }
}
