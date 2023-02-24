package com.zxn.chain.dao;

import com.zxn.chain.dto.OrdersDto;

import java.util.List;

public interface OrdersDao {
    void removeOrder(Long id);

    List<OrdersDto> queryOrderPage(int pageNo, int pageSize,  String orderStatus,Long memberNum);

    int queryOrderCount(int pageNo, int pageSize, String orderStatus,Long memberNum);

    void addOrders(OrdersDto orders);

    void updateOrder(Long id,String orderStatus);

    OrdersDto queryOrderDetail(Long id);

    void addOrderAddress(Long orderId, Long addressId);

    OrdersDto selectOrderById(Long id);
}
