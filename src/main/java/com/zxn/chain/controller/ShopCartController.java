package com.zxn.chain.controller;

import com.zxn.chain.common.CustomException;
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
    @ApiOperation(value = "购物车添加接口(前台)(需memberId)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopCart",value = "信息，通过id判断。id为null,购物中新建商品；id不为null,该商品存在，则加1" +
                    "请求示例{\n" +
                    "  \"memberId\": 1004,\n" +
                    "  \"memberName\": \"李一\",\n" +
                    "  \"memberTel\": \"19935430000\",\n" +
                    "  \"shopName\": \"口红\",\n" +
                    "  \"shopNum\": 2022002,\n" +
                    "  \"shopQuantity\": 1\n" +
                    "}",required = true),
    })
    public Response<String> add(@RequestBody ShopCart shopCart){
        log.info(shopCart.toString());
        ShopCart shop =  shopCartService.queryShopById(shopCart.getMemberId(),shopCart.getShopNum());
        if (shop == null){
            shopCartService.addShopCart(shopCart);
        }else {
            Integer num = shop.getShopQuantity();
            shopCartService.updateShopCartById(shopCart.getMemberId(),shopCart.getShopNum(),num+1);
        }
        return Response.success("购物车添加成功");
    }

    /**
     * 购物车删除
     * @param shopCart
     * @return
     */
    @PostMapping("/del")
    @ApiOperation(value = "购物车删除接口(前台)(需memberId)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopCart",value = "信息，通过id判断。id不为null,该商品存在，减1",required = true),
    })
    public Response<String> del(@RequestBody ShopCart shopCart){
        log.info(shopCart.toString());
        try {
        ShopCart shop =  shopCartService.queryShopById(shopCart.getMemberId(),shopCart.getShopNum());
        if (shop.getShopQuantity() == 1){
            shopCartService.delShopCartById(shopCart.getMemberId(),shopCart.getShopNum());
        }else {
            Integer num = shop.getShopQuantity();
            shopCartService.updateShopCartById(shopCart.getMemberId(),shopCart.getShopNum(),num-1);
        }
        } catch (Exception e) {
            throw new CustomException("购物车为空，无需删除");
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

    /**
     * 购物车结算接口(将购物车内容添加到订单)
     * @return
     */
    @PostMapping("/shopcartbilling")
    @ApiOperation(value = "购物车结算(前台)")
    public Response<String> shopcartBilling(@RequestParam Long memberId){
        shopCartService.shopcartBilling(memberId);
        return Response.success("结算成功,等待付款");
    }
}