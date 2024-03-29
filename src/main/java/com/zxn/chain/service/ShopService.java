package com.zxn.chain.service;

import com.zxn.chain.dto.ShopDto;
import com.zxn.chain.dto.ShopKeyQueryDto;
import com.zxn.chain.model.BasePageResponse;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface ShopService {
    void saveShop(ShopDto shopDto);

    void removeShop(Long[] ids);

    BasePageResponse<ShopDto> queryShopPage(int pageNo, int pageSize, String shopName, String category);

    void updateShop(ShopDto shopDto);

    ShopDto selectShopById(Long id);

    BigDecimal querySellPrice(Long shopNum);

    ArrayList<ShopKeyQueryDto> queryShop(String keyWords);
}
