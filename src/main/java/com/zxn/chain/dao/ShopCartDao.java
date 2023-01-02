package com.zxn.chain.dao;

import com.zxn.chain.entity.ShopCart;

import java.util.ArrayList;

public interface ShopCartDao {
    void saveShopCart(ShopCart shopCart);

    ShopCart queryShopById(Long id);

    void updateShopCartById(Long id, int i);

    void delShopCartById(Long id);

    void delall(Long memberId);

    ArrayList<ShopCart> queryShopCart(Long memberId);
}
