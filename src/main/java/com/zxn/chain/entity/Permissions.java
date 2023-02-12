package com.zxn.chain.entity;

import lombok.Data;

@Data
public class Permissions {
    private Long id;
    /**
     * 权限名
     */
    private String name;
    /**
     * 图片名
     */
    private String icon;
    /**
     * 角色
     */
    private String role;
}
