package com.zxn.chain.service.impl;

import com.zxn.chain.common.CustomException;
import com.zxn.chain.dao.OrdersDao;
import com.zxn.chain.dao.ShopCartDao;
import com.zxn.chain.dao.ShopDao;
import com.zxn.chain.dao.StockDao;
import com.zxn.chain.dto.OrdersDto;
import com.zxn.chain.dto.ShopDto;
import com.zxn.chain.entity.ShopCart;
import com.zxn.chain.service.ShopCartService;
import com.zxn.chain.service.SnowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ShopCartServiceImpl implements ShopCartService {
    @Autowired
    ShopCartDao shopCartDao;
    @Autowired
    OrdersDao ordersDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    StockDao stockDao;
    SnowService snowService = new SnowService(1, 1);
    public void addShopCart(ShopCart shopCart) {
        shopCart.setId(snowService.getId());
        shopCart.setCreateTime(LocalDateTime.now());
        BigDecimal sellPrice = shopDao.querySellPrice(shopCart.getShopNum());
        ShopDto s = shopDao.getShopPic(shopCart.getShopNum());
        System.out.println(s);
        shopCart.setPicture(s.getPicture());
        shopCart.setMoney(sellPrice);
        shopCart.setShopQuantity(1);
        shopCartDao.saveShopCart(shopCart);
    }
    @Override
    public ShopCart queryShopById(Long memberId,Long shopNum) {
        ShopCart shopCart = shopCartDao.queryShopById(memberId,shopNum);
        return shopCart;
    }
    @Override
    public void updateShopCartById(Long memberId,Long shopNum, int i) {
        BigDecimal sellPrice = shopDao.querySellPrice(shopNum);
        shopCartDao.updateShopCartById(memberId,shopNum,i,sellPrice);
    }
    @Override
    public void delShopCartById(Long memberId,Long shopNum) {
        shopCartDao.delShopCartById(memberId,shopNum);
    }
    @Override
    public void delall(Long memberId) {
        shopCartDao.delall(memberId);
    }

    @Override
    public ArrayList<ShopCart> queryShopCart(Long memberId) {
        return shopCartDao.queryShopCart(memberId);
    }

    @Override
    @Transactional
    public void shopcartBilling(Long memberId) {
        ArrayList<ShopCart> arrayList = shopCartDao.queryShopCart(memberId);
        for (ShopCart cart : arrayList) {
            OrdersDto orders = new OrdersDto();
            orders.setId(snowService.getId());
            orders.setOrderTime(LocalDateTime.now());
            orders.setShopNum(cart.getShopNum());
            orders.setProductName(cart.getShopName());
            orders.setMemberId(memberId);
            orders.setMemberTel(cart.getMemberTel());
            orders.setShopQuantity(cart.getShopQuantity());
            orders.setOrderStatus("待付款");
            orders.setRemarks("无");
            BigDecimal decimal = cart.getMoney();
            orders.setProductMoney(decimal);
            ordersDao.addOrders(orders);
            //判断库存是否足够
            Integer remainder = shopDao.getRemainder(cart.getShopNum());
            if (remainder<cart.getShopQuantity()){
                throw new CustomException("库存余量不足");
            }
            //扣除商品库存
//        stock.sal_num(销售数量)  +shop_cat.shop_quantity
//        shop.quantity(剩余数量) -shop_cat.shop_quantity
//        stock.sal_num + shop.quantity = stock.start_num(进货数量)
            stockDao.addSalNum(cart.getShopNum(),cart.getShopQuantity());
            shopDao.delSalNum(cart.getShopNum(),cart.getShopQuantity());
        }
        //删除购物车记录
        shopCartDao.delall(memberId);


    }
}
