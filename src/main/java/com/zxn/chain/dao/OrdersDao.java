package com.zxn.chain.dao;

import com.zxn.chain.dto.OrdersDto;
import com.zxn.chain.entity.Orders;

import java.util.List;

public interface OrdersDao {
    void removeOrder(Long id);

    List<OrdersDto> queryOrderPage(int pageNo, int pageSize, String orderNum, String orderStatus);

    int queryOrderCount(int pageNo, int pageSize, String orderNum, String orderStatus);

    void addOrders(OrdersDto orders);

    void updateOrder(Long id,String orderStatus);

    OrdersDto queryOrderDetail(Long id);
}
