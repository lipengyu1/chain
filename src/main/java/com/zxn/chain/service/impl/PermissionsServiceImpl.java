package com.zxn.chain.service.impl;

import com.zxn.chain.dao.PermissionsDao;
import com.zxn.chain.dto.PermissionCategoryDto;
import com.zxn.chain.dto.PermissionsDto;
import com.zxn.chain.entity.Permissions;
import com.zxn.chain.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PermissionsServiceImpl implements PermissionsService {
    @Autowired
    PermissionsDao permissionsDao;

    @Override
    public ArrayList<PermissionCategoryDto> queryPermiss(String role) {
        ArrayList<PermissionCategoryDto> arrayList =  permissionsDao.queryPermiss(role);
        return arrayList;
    }

    @Override
    public ArrayList<PermissionsDto> queryChild(String role,Long id) {
        ArrayList<PermissionsDto> arrayList =  permissionsDao.queryChild(role,id);
        return arrayList;
    }
}
