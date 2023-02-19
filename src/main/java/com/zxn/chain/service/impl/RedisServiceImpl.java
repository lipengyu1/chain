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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Override
    public void saveHistory(Long memberNum, LocalDateTime date, Long id) {
        String key = memberNum+"history";//userId-用户Num(key),id-商品id，非商品num(hashkey)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String datetime = date.format(formatter);//datetime-阅读时间(value)
        redisTemplate.opsForHash().put(key,id,datetime);
    }

    @Override
    public void delHistory(Long memberNum, Long id) {
        String key = memberNum+"history";
        redisTemplate.opsForHash().delete(key,id);
    }

    @Override
    public Map queryHistory(Long memberNum) {
        String key = memberNum+"history";
        Map map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    @Override
    public void saveUserQuery(String KeyWords, Long memberNum) {
        String key = memberNum+"keywords";
        redisTemplate.opsForList().leftPush(key, KeyWords);
        Long size = redisTemplate.opsForList().size(memberNum);
        if (size > 100) {
            redisTemplate.opsForList().rightPop(memberNum);
        }
    }

    @Override
    public List getUserQuery(Long memberNum) {
        String key = memberNum+"keywords";
        List list = redisTemplate.opsForList().range(key,0,-1);
        return list ;
    }
}

