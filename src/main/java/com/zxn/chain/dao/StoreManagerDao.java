package com.zxn.chain.dao;

import com.zxn.chain.dto.StoreManagerDto;
import com.zxn.chain.entity.StoreManager;

import java.util.List;

public interface StoreManagerDao {
    List<StoreManagerDto> queryStoreManagerPage(int pageNo, int pageSize, String storeEmployeeName, String storeSaleStatus);

    int queryStoreManagerCount(int pageNo, int pageSize, String storeEmployeeName, String storeSaleStatus);

    StoreManager selectStoreManagerById(Long id);

    void removeStoreManager(Long id);

    void updateStoreManager(StoreManager storeManager);

    StoreManager storeManagerLogin(StoreManager storeManager);

    void setNewPasswd(String email, String newPasswd);

    StoreManager getEmpEmail(String email);

    void managerRegister(StoreManager storeManager);

    StoreManager selectStoreManagerByNumber(Long managerId);
}
