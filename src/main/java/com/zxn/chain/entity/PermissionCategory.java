package com.zxn.chain.entity;

import lombok.Data;

@Data
public class PermissionCategory {
    private Long id;
    /**
     * 权限名
     */
    private String bigName;
    /**
     * 图片
     */
    private String icon;
    /**
     * 角色
     */
    private String role;
}
