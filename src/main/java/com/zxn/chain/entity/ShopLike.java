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
    private Long memberId;
    /**
     * 点赞状态
     */
    private Integer state;

    public ShopLike(Long shopId, Long memberId, Integer state) {
        this.shopId = shopId;
        this.memberId = memberId;
        this.state = state;
    }
}
