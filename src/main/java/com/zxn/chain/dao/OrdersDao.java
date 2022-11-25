package com.zxn.chain.dao;

import com.zxn.chain.dto.OrdersDto;

import java.util.List;

public interface OrdersDao {
    void removeOrder(Long id);

    List<OrdersDto> queryOrderPage(int pageNo, int pageSize, String orderNum, String orderStatus);

    int queryOrderCount(int pageNo, int pageSize, String orderNum, String orderStatus);
}
