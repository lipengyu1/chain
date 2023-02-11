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
    public void saveLiked2Redis(Long shopId, Long memberId) {
        String key = RedisKeyUtils.getLikedKey(shopId, memberId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_MEMBER_LIKED,
                key, LikedStatusEnum.LIKE.getCode());
    }
    @Override
    public void unlikeFromRedis(Long shopId, Long memberId) {
        String key = RedisKeyUtils.getLikedKey(shopId, memberId);
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
            String member = split[1];
            Long memberId = Long.valueOf(member);
            Integer state = (Integer) entry.getValue();
            //组装成 ShopLike 对象
            ShopLike shopLike = new ShopLike(shopId, memberId, state);
            list.add(shopLike);
            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_MEMBER_LIKED, key);
        }
        return list;
    }
}

