package com.zxn.chain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MemberCollectionDto implements Serializable {
    private Long id;
    /**
     *商品名称
     */
    private String shopName;
    /**
     * 商品编号
     */
    private Long shopNum;
    /**
     * 图片
     */
    private String picture;
    /**
     * 售价
     */
    private BigDecimal sellPrice;
    /**
     * 商品分类
     */
    private String category;
    /**
     * 点赞量
     */
    private Integer likeCount;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    private String shopColor;
    private String shopTag;
    private String shopSubname;
    /**
     * 商品id
     */
    private Long shopId;
}
