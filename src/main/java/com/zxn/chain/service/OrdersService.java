package com.zxn.chain.service;

import com.zxn.chain.dto.OrdersDto;
import com.zxn.chain.model.BasePageResponse;

public interface OrdersService {
    void removeOrder(Long[] ids);

    BasePageResponse<OrdersDto> queryOrderPage(int pageNo, int pageSize, String orderNum, String orderStatus,Long memberNum);

    void updateOrder(OrdersDto orderDto);

    void rollBackShopNum(Long id);

    void addOrderAddress(Long orderId, Long addressId);
}
