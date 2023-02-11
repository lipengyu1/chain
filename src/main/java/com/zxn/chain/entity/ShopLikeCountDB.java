package com.zxn.chain.entity;

import lombok.Data;

@Data
public class ShopLikeCountDB {
    /**
     * 商品id
     */
    private Long shopId;
    /**
     * 点赞数量
     */
    private Integer likedCount;
}
