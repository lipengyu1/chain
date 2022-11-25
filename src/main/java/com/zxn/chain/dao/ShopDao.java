package com.zxn.chain.dao;

import com.zxn.chain.dto.ShopDto;
import com.zxn.chain.entity.Shop;

import java.util.ArrayList;
import java.util.List;

public interface ShopDao {
    void saveShop(ShopDto shopDto);

    void removeShop(Long id);

    List<ShopDto> queryShopPage(int pageNo, int pageSize, String shopName, String category);

    int queryShopCount(int pageNo, int pageSize, String shopName, String category);

    void updateShop(ShopDto shopDto);

    ShopDto selectShopById(Long id);

    List<ShopDto> selectShopBySupplierId(Long id);

    Shop  selectShopNameByShopId(Long shopId);
}
