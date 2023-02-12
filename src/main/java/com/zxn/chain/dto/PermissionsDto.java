package com.zxn.chain.dto;

import lombok.Data;

@Data
public class PermissionsDto {
    private Long id;
    /**
     * 权限名
     */
    private String name;
    /**
     * 图片名
     */
    private String icon;
}
