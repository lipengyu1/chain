package com.zxn.chain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StoreDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 门店编号
     */
    private Integer storeNum;
    /**
     * 门店类型
     */
    private String storeType;
    /**
     * 区域
     */
    private String storeArea;
    /**
     * 默认配货方
     */
    private String defaultShipper;
    /**
     * 负责人id
     */
    private Long storeLeaderId;
    /**
     * 负责人姓名
     */
    private String storeLeaderName;
    /**
     * 电话
     */
    private String storeTel;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 备注
     */
    private String remarks;
}
