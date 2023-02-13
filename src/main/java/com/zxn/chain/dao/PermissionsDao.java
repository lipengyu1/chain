package com.zxn.chain.dao;


import com.zxn.chain.dto.PermissionCategoryDto;
import com.zxn.chain.dto.PermissionsDto;

import java.util.ArrayList;


public interface PermissionsDao {
    ArrayList<PermissionCategoryDto> queryPermiss(String role);

    ArrayList<PermissionsDto> queryChild(String role,Long id);
}
