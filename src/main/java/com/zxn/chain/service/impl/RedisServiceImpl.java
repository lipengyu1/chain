package com.zxn.chain.service.impl;

import com.zxn.chain.entity.ShopLike;
import com.zxn.chain.entity.ShopLikeCount;
import com.zxn.chain.service.RedisService;
import com.zxn.chain.utils.LikedStatusEnum;
import com.zxn.chain.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void saveLiked2Redis(Long shopId, Long userId) {
        String key = RedisKeyUtils.getLikedKey(shopId, userId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_MEMBER_LIKED,
                key, LikedStatusEnum.LIKE.getCode());
    }
    @Override
    public void unlikeFromRedis(Long shopId, Long userId) {
        String key = RedisKeyUtils.getLikedKey(shopId, userId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_MEMBER_LIKED,
                key, LikedStatusEnum.UNLIKE.getCode());
    }
    @Transactional
    @Override
    public void incrementLikedCount(Long shopId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_SHOP_LIKED_COUNT,shopId,1);
    }
    @Transactional
    @Override
    public void decrementLikedCount(Long shopId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_SHOP_LIKED_COUNT,shopId,-1);
    }
//   获取商品点赞详细数据
    @Override
    public List<ShopLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor =redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_MEMBER_LIKED, ScanOptions.NONE);
        List<ShopLike> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 shopId，userId
            String[] split = key.split("::");
            String shop = split[0];
            Long shopId = Long.valueOf(shop);
            String user = split[1];
            Long userId = Long.valueOf(user);
            Integer state = (Integer) entry.getValue();
            //组装成 UserLike 对象
            ShopLike newsLike = new ShopLike(shopId, userId, state);
            list.add(newsLike);
            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_MEMBER_LIKED, key);
        }        return list;
    }
//    获取商品点赞量
    @Override
    public List<ShopLikeCount> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor =    redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_SHOP_LIKED_COUNT,  ScanOptions.NONE);
        List<ShopLikeCount> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储
            ShopLikeCount dto = new ShopLikeCount((Long) map.getKey(),(Integer)map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_SHOP_LIKED_COUNT, (Long) map.getKey());
        }
        return list;
    }
}

