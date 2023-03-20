package com.zxn.chain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberCollection {
    private Long id;
    /**
     * 会员编号
     */
    private Long memberNum;
    /**
     * 商品编号
     */
    private Long shopNum;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 商品id
     */
    private Long shopId;
}
