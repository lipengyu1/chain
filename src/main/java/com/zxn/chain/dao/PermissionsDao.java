package com.zxn.chain.dao;


import com.zxn.chain.dto.PermissionsDto;

import java.util.ArrayList;

public interface PermissionsDao {
    ArrayList<PermissionsDto> queryPermiss(String role);
}
