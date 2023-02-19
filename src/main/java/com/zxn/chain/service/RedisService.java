package com.zxn.chain.service;


import com.zxn.chain.entity.ShopLike;
import com.zxn.chain.entity.ShopLikeCount;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    /**
     * 保存用户浏览记录
     */
    void saveHistory(Long memberNum, LocalDateTime date, Long id);
    /**
     *删除用户历史记录
     */
    void delHistory(Long memberNum, Long id);
    /**
     *查询用户历史记录
     */
    Map queryHistory(Long memberNum);
    /**
     * 用户搜索记录保存至redis
     */
    void saveUserQuery(String KeyWords,Long memberNum);
    /**
     * 查询用户输入记录
     */
    List getUserQuery(Long memberNum);
}
