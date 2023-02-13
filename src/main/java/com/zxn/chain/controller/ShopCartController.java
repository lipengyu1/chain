package com.zxn.chain.controller;

import com.zxn.chain.entity.ShopCart;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.ShopCartServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/shopcart")
@Api(tags = "购物车接口")
public class ShopCartController {
    @Autowired
    private ShopCartServiceImpl shopCartService;
    /**
     * 购物车添加
     * @param shopCart
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "购物车添加接口(前台)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopCart",value = "信息，通过id判断。id为null,购物中新建商品；id不为null,该商品存在，则加1",required = true),
    })
    public Response<String> add(@RequestBody ShopCart shopCart){
        log.info(shopCart.toString());
        ShopCart shop =  shopCartService.queryShopById(shopCart.getId());
        if (shop == null){
            shopCartService.addShopCart(shopCart);
        }else {
            Integer num = shop.getShopQuantity();
            shopCartService.updateShopCartById(shopCart.getId(),num+1);
        }
        return Response.success("购物车添加成功");
    }

    /**
     * 购物车删除
     * @param shopCart
     * @return
     */
    @PostMapping("/del")
    @ApiOperation(value = "购物车删除接口(前台)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopCart",value = "信息，通过id判断。id不为null,该商品存在，减1",required = true),
    })
    public Response<String> del(@RequestBody ShopCart shopCart){
        log.info(shopCart.toString());
        ShopCart shop =  shopCartService.queryShopById(shopCart.getId());
        if (shop.getShopQuantity() == 1){
            shopCartService.delShopCartById(shopCart.getId());
        }else {
            Integer num = shop.getShopQuantity();
            shopCartService.updateShopCartById(shopCart.getId(),num-1);
        }
        return Response.success("购物车删除成功");
    }

    /**
     * 清空购物车
     * @param memberId
     * @return
     */
    @PostMapping("/delall")
    @ApiOperation(value = "购物车清空接口(前台)")
    public Response<String> delall(@RequestParam Long memberId){
        log.info(memberId.toString());
        shopCartService.delall(memberId);
        return Response.success("清空购物车成功");
    }

    /**
     * 购物车查询接口
     * @param memberId
     * @return
     */
    @GetMapping("/queryshopcart")
    @ApiOperation(value = "购物车查询接口(前台)")
    public Response<ArrayList<ShopCart>> queryShopCart(@RequestParam Long memberId){
        log.info(memberId.toString());
        ArrayList<ShopCart> arrayList = shopCartService.queryShopCart(memberId);
        return Response.success(arrayList);
    }
}