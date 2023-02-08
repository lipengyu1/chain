package com.zxn.chain.controller;

import com.zxn.chain.model.Response;
import com.zxn.chain.service.LikedService;
import com.zxn.chain.service.impl.LikedServiceImpl;
import com.zxn.chain.service.impl.RedisServiceImpl;
import com.zxn.chain.utils.JwtUtils;
import com.zxn.chain.utils.RedisKeyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/shoplike")
@Api(tags = "商品点赞接口")
public class ShopLikeController {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisServiceImpl redisService;
    @Autowired
    LikedServiceImpl likedService;
    /**
     * 点赞
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "点赞接口")
    public Response<String> like(HttpServletRequest request,@RequestParam Long shopId){
        Long userId = Long.valueOf(JwtUtils.getUserId(request.getHeader("token")));
        //判断是否是第一次点赞
        String id = shopId+"::"+userId;
        boolean liked = redisTemplate.opsForHash().hasKey(RedisKeyUtils.MAP_MEMBER_LIKED,id);
        if ((liked==false)||(redisTemplate.opsForHash().get(RedisKeyUtils.MAP_MEMBER_LIKED,id).equals(0))){
            redisService.saveLiked2Redis(shopId,userId);
            redisService.incrementLikedCount(shopId);
            return Response.success("点赞成功");
        }
        return Response.success("点赞失败，已点赞");
    }
    /**
     * 取消点赞
     * @return
     */
    @PostMapping("/sub")
    @ApiOperation(value = "取消点赞接口")
    public Response<String> nolike(HttpServletRequest request,@RequestParam Long shopId){
        Long userId = Long.valueOf(JwtUtils.getUserId(request.getHeader("token")));
        String id = shopId+"::"+userId;
        boolean liked = redisTemplate.opsForHash().hasKey(RedisKeyUtils.MAP_MEMBER_LIKED,id);
        System.out.println(liked);
        System.out.println(redisTemplate.opsForHash().get(RedisKeyUtils.MAP_MEMBER_LIKED,id));
        if ((liked==true)&&(redisTemplate.opsForHash().get(RedisKeyUtils.MAP_MEMBER_LIKED,id).equals(1))){
            redisService.unlikeFromRedis(shopId,userId);
            redisService.decrementLikedCount(shopId);
            return Response.success("取消点赞成功");
        }
        return Response.success("取消失败");
    }


//    //手动同步接口
//    @GetMapping("/get1")
//    public Response<String> get1(){
//        likedService.transLikedFromRedis2DB();
//        return Response.success("ok");
//    }
//
//    @GetMapping("/get2")
//    public Response<String> get2(){
//        likedService.transLikedCountFromRedis2DB();
//        return Response.success("ok");
//    }
}
