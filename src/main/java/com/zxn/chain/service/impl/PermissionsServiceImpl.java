package com.zxn.chain.service.impl;

import com.zxn.chain.dao.PermissionsDao;
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
    public ArrayList<PermissionsDto> queryPermiss(String role) {
        ArrayList<PermissionsDto> arrayList =  permissionsDao.queryPermiss(role);
        return arrayList;
    }
}
