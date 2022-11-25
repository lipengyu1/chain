package com.zxn.chain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrdersDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 订单编号
     */
    private Long orderNum;
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

}
