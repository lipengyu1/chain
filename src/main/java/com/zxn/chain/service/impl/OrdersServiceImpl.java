package com.zxn.chain.service.impl;

import com.zxn.chain.dao.AddressDao;
import com.zxn.chain.dao.OrdersDao;
import com.zxn.chain.dao.ShopDao;
import com.zxn.chain.dao.StockDao;
import com.zxn.chain.dto.OrdersDto;
import com.zxn.chain.dto.ShopDto;
import com.zxn.chain.entity.Address;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    AddressDao addressDao;
    @Autowired
    OrdersDao orderDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    StockDao stockDao;
    @Override
    public void removeOrder(Long[] ids) {
        for (int i = 0; i <ids.length; i++) {
            Long id = ids[i];
            orderDao.removeOrder(id);
        }
    }

    @Override
    public BasePageResponse<OrdersDto> queryOrderPage(int pageNo, int pageSize, String orderNum, String orderStatus,Long memberNum) {
        int pageNo1 = pageSize * (pageNo - 1);
        List<OrdersDto> queryList = orderDao.queryOrderPage(pageNo1,pageSize,orderNum,orderStatus,memberNum);
        //获取地址详细信息
        for (OrdersDto ordersDto : queryList) {
            Address address = addressDao.selectAddressById(ordersDto.getAddressId());
            ordersDto.setAddress(address.getAddress());
        }
        ArrayList<OrdersDto> arrayList = new ArrayList<>(queryList);
        int totalCount = orderDao.queryOrderCount(pageNo1,pageSize,orderNum,orderStatus,memberNum);
        BasePageResponse<OrdersDto> basePageResponse = new BasePageResponse<>();
        basePageResponse.setPageNo(pageNo);
        basePageResponse.setPageSize(pageSize);
        basePageResponse.setTotalPage((int)Math.ceil((float)totalCount/pageSize));
        basePageResponse.setResultList(arrayList);
        basePageResponse.setTotalCount(totalCount);
        return basePageResponse;
    }

    @Override
    public void updateOrder(OrdersDto orderDto) {
        orderDao.updateOrder(orderDto.getId(),orderDto.getOrderStatus());
    }

    @Override
    public void rollBackShopNum(Long id) {
        //获取订单中的shop_num与shop_quantity
        OrdersDto ordersDto = orderDao.queryOrderDetail(id);
        Long shopNum = ordersDto.getShopNum();
        Integer shopQuantity =  ordersDto.getShopQuantity();
        stockDao.delSalNum(shopNum,shopQuantity);
        shopDao.addSalNum(shopNum,shopQuantity);
    }

    @Override
    public void addOrderAddress(Long orderId, Long addressId) {
        orderDao.addOrderAddress(orderId,addressId);
    }
}
