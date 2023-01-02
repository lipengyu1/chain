package com.zxn.chain.service.impl;

import com.zxn.chain.dao.ShopCartDao;
import com.zxn.chain.entity.ShopCart;
import com.zxn.chain.service.ShopCartService;
import com.zxn.chain.service.SnowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ShopCartServiceImpl implements ShopCartService {
    @Autowired
    ShopCartDao shopCartDao;
    SnowService snowService = new SnowService(1, 1);
    public void addShopCart(ShopCart shopCart) {
        shopCart.setId(snowService.getId());
        shopCart.setCreateTime(LocalDateTime.now());
        shopCartDao.saveShopCart(shopCart);
    }
    @Override
    public ShopCart queryShopById(Long id) {
        ShopCart shopCart = shopCartDao.queryShopById(id);
        return shopCart;
    }
    @Override
    public void updateShopCartById(Long id, int i) {
        shopCartDao.updateShopCartById(id,i);
    }
    @Override
    public void delShopCartById(Long id) {
        shopCartDao.delShopCartById(id);
    }
    @Override
    public void delall(Long memberId) {
        shopCartDao.delall(memberId);
    }

    @Override
    public ArrayList<ShopCart> queryShopCart(Long memberId) {
        return shopCartDao.queryShopCart(memberId);
    }
}
