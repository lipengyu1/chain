package com.zxn.chain.service.impl;


import com.zxn.chain.dao.ShopLikeDao;
import com.zxn.chain.entity.ShopLike;
import com.zxn.chain.entity.ShopLikeCount;
import com.zxn.chain.entity.ShopLikeCountDB;
import com.zxn.chain.entity.ShopLikeDB;
import com.zxn.chain.service.LikedService;
import com.zxn.chain.service.RedisService;
import com.zxn.chain.service.SnowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class LikedServiceImpl implements LikedService {

    @Autowired
    ShopLikeDao shopLikeDao;
    @Autowired
    RedisService redisService;
    SnowService snowService = new SnowService(1, 1);
//  redis点赞详细信息缓存到数据库
    @Override
    @Transactional
    public void transLikedFromRedis2DB() {
        List<ShopLike> list = redisService.getLikedDataFromRedis();
        for (ShopLike like : list) {
            ShopLikeDB shopLikeDB = new ShopLikeDB();
            shopLikeDB.setShopId(like.getShopId());
            shopLikeDB.setMemberId(like.getMemberId());
            shopLikeDB.setId(snowService.getId());
            shopLikeDB.setState(like.getState());
            shopLikeDB.setUpdateTime(LocalDateTime.now());
            shopLikeDao.saveShopLike(shopLikeDB);
        }
    }
//  redis文章点赞量缓存到数据库
    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {
        List<ShopLikeCount> list = redisService.getLikedCountFromRedis();
        for (ShopLikeCount dto : list) {
            ShopLikeCountDB shopLikeCountDB = new ShopLikeCountDB();
            shopLikeCountDB.setId(snowService.getId());
            shopLikeCountDB.setShopId(dto.getShopId());
            shopLikeCountDB.setLikedCount(dto.getShopLikeCount());
            shopLikeDao.saveShopLikeCount(shopLikeCountDB);
        }
    }
}

