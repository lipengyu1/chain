package com.zxn.chain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Orders {
    private static final long serialVersionUID = 1L;
    private Long id;//订单编号
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 金额
     */
    private BigDecimal productMoney;
    /**
     * 下单时间
     */
    private LocalDateTime orderTime;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 商品编号
     */
    private Long shopNum;
    /**
     * 顾客id
     */
    private Long memberId;
    /**
     * 1正常0删除
     */
    private Integer status;
    /**
     * 数量
     */
    private Integer shopQuantity;
    /**
     * 电话
     */
    private String memberTel;
    /**
     * 地址id
     */
    private Long addressId;
}
