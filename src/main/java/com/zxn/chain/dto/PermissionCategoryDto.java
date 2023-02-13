package com.zxn.chain.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PermissionCategoryDto {
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
     * 权限小类
     */
    private ArrayList<PermissionsDto> children;
}
