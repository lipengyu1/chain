package com.zxn.chain.service;

import com.zxn.chain.dto.PermissionsDto;

import java.util.ArrayList;


public interface PermissionsService {
    ArrayList<PermissionsDto> queryPermiss(String role);
}
