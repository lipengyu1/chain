package com.zxn.chain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopLikeDB {
    private Long id;
    /**
     * 用户id
     */
    private Long memberId;
    /**
     * 新闻id
     */
    private Long shopId;
    /**
     * 点赞状态
     */
    private Integer state;
    /**
     * 更新入库时间
     */
    private LocalDateTime updateTime;
}
