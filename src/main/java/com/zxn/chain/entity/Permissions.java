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
     * 角色
     */
    private String role;
}
