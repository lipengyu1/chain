package com.zxn.chain.dao;

import com.zxn.chain.dto.StoreDto;
import com.zxn.chain.entity.Store;

import java.util.List;

public interface StoreDao {
    void saveStore(StoreDto storeDto);

    void removeStore(Long id);

    List<StoreDto> selectByDefaultShipper(String defaultShipper);

    List<StoreDto> queryStorePage(int pageNo, int pageSize, String storeType, String storeArea, String storeLeaderName);

    int querySupplierCount(int pageNo, int pageSize, String storeType, String storeArea, String storeLeaderName);

    void updateStore(StoreDto storeDto);

    StoreDto selectStoreById(Long id);

    Store selectStoreByNum(Integer storeNum);
}
