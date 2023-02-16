package com.zxn.chain.dao;

import com.zxn.chain.entity.ShopCart;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface ShopCartDao {
    void saveShopCart(ShopCart shopCart);

    ShopCart queryShopById(Long memberId, Long shopNum);

    void updateShopCartById(Long memberId,Long shopNum, int i,BigDecimal sellPrice);

    void delShopCartById(Long memberId,Long shopNum);

    void delall(Long memberId);

    ArrayList<ShopCart> queryShopCart(Long memberId);
}
