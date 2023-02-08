package com.zxn.chain.entity;

import lombok.Data;

@Data
public class ShopLikeCountDB {
    private Long id;
    /**
     * 商品id
     */
    private Long shopId;
    /**
     * 点赞数量
     */
    private Integer likedCount;
}
