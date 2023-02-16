package com.zxn.chain.service;


import com.zxn.chain.entity.ShopCart;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface ShopCartService {

    ShopCart queryShopById(Long memberId,Long shopNum);

    void updateShopCartById(Long memberId,Long shopNum, int i);

    void delShopCartById(Long memberId,Long shopNum);

    void delall(Long memberId);

    ArrayList<ShopCart> queryShopCart(Long memberId);

    void shopcartBilling(Long memberId);
}
