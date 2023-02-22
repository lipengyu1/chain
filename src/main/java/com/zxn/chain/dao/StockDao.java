package com.zxn.chain.dao;

import com.zxn.chain.dto.StockDto;
import com.zxn.chain.dto.WarnDto;

import java.util.ArrayList;
import java.util.List;

public interface StockDao {
    void removeStock(Long id);

    List<StockDto> queryStockPage(int pageNo, int pageSize, String shopSupplier, String shopCategory);

    int queryStockCount(int pageNo, int pageSize, String shopSupplier, String shopCategory);

    StockDto selectStockById(Long id);

    void saveStock(StockDto stockDto);

    void updateStock(StockDto stockDto);

    void addSalNum(Long shopNum, Integer shopQuantity);

    void delSalNum(Long shopNum, Integer shopQuantity);

    List<String> getWarnShopName();

    List<String> getShopSellNum();
}
