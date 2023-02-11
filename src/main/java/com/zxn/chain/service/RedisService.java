package com.zxn.chain.service;


import com.zxn.chain.entity.ShopLike;
import com.zxn.chain.entity.ShopLikeCount;

import java.util.List;

public interface RedisService {
    /**
     * 点赞。状态为1
     */
    void saveLiked2Redis(Long shopId, Long memberId);
    /**
     * 取消点赞。将状态改变为0
     */
    void unlikeFromRedis(Long shopId, Long memberId);
    /**
     * 该文章的点赞数加1
     */
    void incrementLikedCount(Long shopId);
    /**
     * 该文章的点赞数减1
     */
    void decrementLikedCount(Long shopId);
    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<ShopLike> getLikedDataFromRedis();

}
