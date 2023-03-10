package com.zxn.chain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShopCart {
    private Long id;
    /**
     * 会员（顾客id）
     */
    private Long memberId;
    /**
     * 姓名
     */
    private String memberName;
    /**
     * 商品id
     */
    private Long shopNum;
    /**
     * 商品名
     */
    private String shopName;
    /**
     * 数量
     */
    private Integer shopQuantity;
    /**
     * 电话
     */
    private String memberTel;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 图片
     */
    private String picture;
}
