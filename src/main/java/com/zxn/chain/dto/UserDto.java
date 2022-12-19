package com.zxn.chain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 姓名
     */
    private String managerName;
    /**
     * 性别
     */
    private String managerSex;
    /**
     * 职称
     */
    private String managerProfessional;
    /**
     * 管理级别
     */
    private Integer managerLevel;
    /**
     * 电话
     */
    private String managerTel;
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String managerEmail;
    /**
     * 状态1正常0删除
     */
    private Integer status;
    /**
     * 角色
     */
    private String role;
    /**
     * 备注
     */
    private String remarks;
    /**
     * token
     */
    private String token;
}
