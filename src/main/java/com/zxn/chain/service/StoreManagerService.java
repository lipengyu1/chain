package com.zxn.chain.service;

import com.zxn.chain.dto.StoreManagerDto;
import com.zxn.chain.entity.StoreManager;
import com.zxn.chain.model.BasePageResponse;

public interface StoreManagerService {
    BasePageResponse<StoreManagerDto> queryStoreManagerPage(int pageNo, int pageSize, String storeEmployeeName, String storeSaleStatus);

    StoreManager selectStoreManagerById(Long id);

    void removeStoreManager(Long[] ids);

    void updateStoreManager(StoreManager storeManager);

    StoreManager storeManagerLogin(StoreManager storeManager);

    void setNewPasswd(String email, String newPasswd);

    StoreManager getEmpEmail(String email);

    void managerRegister(StoreManager storeManager);
}
