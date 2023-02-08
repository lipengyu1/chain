package com.zxn.chain.dao;


import com.zxn.chain.entity.ShopLikeCountDB;
import com.zxn.chain.entity.ShopLikeDB;

public interface ShopLikeDao {
    void saveShopLike(ShopLikeDB shopLikeDB);

    void saveShopLikeCount(ShopLikeCountDB shopLikeCountDB);
}
