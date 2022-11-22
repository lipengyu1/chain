package com.zxn.chain.service;

import com.zxn.chain.dto.StoreDto;
import com.zxn.chain.model.BasePageResponse;

public interface StoreService {
    void saveStore(StoreDto storeDto);

    void removeStore(Long[] ids);

    BasePageResponse<StoreDto> queryStorePage(int pageNo, int pageSize, String storeType, String storeArea, String storeLeaderName);

    void updateStore(StoreDto storeDto);

    StoreDto selectStoreById(Long id);
}
