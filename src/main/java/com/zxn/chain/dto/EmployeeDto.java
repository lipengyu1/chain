package com.zxn.chain.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
}
