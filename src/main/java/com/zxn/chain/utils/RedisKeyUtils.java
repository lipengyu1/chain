package com.zxn.chain.utils;

public class RedisKeyUtils {

    //保存用户点赞数据的key
    public static final String MAP_MEMBER_LIKED = "MAP_MEMBER_LIKED";
    //保存商品被点赞数量的key
    public static final String MAP_SHOP_LIKED_COUNT = "MAP_SHOP_LIKED_COUNT";

    /**
     * 拼接被点赞的商品id和点赞的用户的id作为key。格式 001::1006
     * @return
     */
    public static String getLikedKey(Long newsId, Long userId){
        StringBuilder builder = new StringBuilder();
        builder.append(newsId);
        builder.append("::");
        builder.append(userId);
        return builder.toString();
    }
}

