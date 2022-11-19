package com.zxn.chain.service.impl;

import com.zxn.chain.dao.StoreManagerDao;
import com.zxn.chain.dto.StoreManagerDto;
import com.zxn.chain.entity.StoreManager;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.service.SnowService;
import com.zxn.chain.service.StoreManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreManagerServiceImpl implements StoreManagerService {
    @Autowired
    StoreManagerDao storeManagerDao;

    SnowService snowService = new SnowService(1, 1);

    @Override
    public BasePageResponse<StoreManagerDto> queryStoreManagerPage(int pageNo, int pageSize, String storeEmployeeName, String storeSaleStatus) {
        int pageNo1 = pageSize * (pageNo - 1);
        List<StoreManagerDto> queryList = storeManagerDao.queryStoreManagerPage(pageNo1,pageSize,storeEmployeeName,storeSaleStatus);
        ArrayList<StoreManagerDto> arrayList = new ArrayList<>(queryList);
        int totalCount = storeManagerDao.queryStoreManagerCount(pageNo1,pageSize,storeEmployeeName,storeSaleStatus);
        BasePageResponse<StoreManagerDto> basePageResponse = new BasePageResponse<>();
        basePageResponse.setPageNo(pageNo);
        basePageResponse.setPageSize(pageSize);
        basePageResponse.setTotalPage((int)Math.ceil((float)totalCount/pageSize));
        basePageResponse.setResultList(arrayList);
        basePageResponse.setTotalCount(totalCount);
        return basePageResponse;
    }

    @Override
    public StoreManager selectStoreManagerById(Long id) {
        return storeManagerDao.selectStoreManagerById(id);
    }

    @Override
    public void removeStoreManager(Long[] ids) {
        for (int i = 0; i <ids.length; i++) {
            Long id = ids[i];
            storeManagerDao.removeStoreManager(id);
        }
    }

    @Override
    public void updateStoreManager(StoreManager storeManager) {
        storeManagerDao.updateStoreManager(storeManager);
    }

    @Override
    public StoreManager storeManagerLogin(StoreManager storeManager) {
        return storeManagerDao.storeManagerLogin(storeManager);
    }

    @Override
    public void setNewPasswd(String email, String newPasswd) {
        storeManagerDao.setNewPasswd(email,newPasswd);
    }

    @Override
    public StoreManager getEmpEmail(String email) {
        return storeManagerDao.getEmpEmail(email);
    }

    @Override
    public void managerRegister(StoreManager storeManager) {
        String password = storeManager.getPassword();
        //密码加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        storeManager.setPassword(password);
        storeManager.setId(snowService.getId());
        storeManagerDao.managerRegister(storeManager);
    }
}
