package com.zxn.chain.service;

import com.zxn.chain.dto.StockDto;
import com.zxn.chain.model.BasePageResponse;

public interface StockService {
    void saveStock(StockDto shopDto);

    void removeStock(Long[] ids);

    BasePageResponse<StockDto> queryShopPage(int pageNo, int pageSize, String shopSupplier, String shopCategory);

    void updateStock(StockDto stockDto);

    StockDto selectStockById(Long id);
}
