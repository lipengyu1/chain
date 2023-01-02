package com.zxn.chain.service;


import com.zxn.chain.entity.ShopCart;

import java.util.ArrayList;

public interface ShopCartService {

    ShopCart queryShopById(Long id);

    void updateShopCartById(Long id, int i);

    void delShopCartById(Long id);

    void delall(Long memberId);

    ArrayList<ShopCart> queryShopCart(Long memberId);
}
