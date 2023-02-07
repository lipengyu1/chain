package com.zxn.chain.entity;


import lombok.Data;

@Data
public class ShopLike {
    /**
     * 商品id
     */
    private Long shopId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 点赞状态
     */
    private Integer state;

    public ShopLike(Long shopId, Long userId, Integer state) {
        this.shopId = shopId;
        this.userId = userId;
        this.state = state;
    }
}
