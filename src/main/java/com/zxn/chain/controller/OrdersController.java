package com.zxn.chain.controller;


import com.zxn.chain.dto.OrdersDto;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.OrdersServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
@Api(tags = "订单信息管理相关接口")
public class OrdersController {

    @Autowired
    private OrdersServiceImpl orderService;

    /**
     * 删除订单（单选、多选）逻辑删除
     * @param ids
     * @return
     */
    @PutMapping("/del")
    @ApiOperation(value = "删除订单接口(前后台)")
    public Response<String> delete(@RequestParam Long[] ids){
        log.info("ids:{}",ids);
        orderService.removeOrder(ids);
        return Response.success("订单删除成功");
    }

    /**
     * 订单分页查询
     * @param pageNo
     * @param pageSize
     * @param orderNum
     * @param orderStatus
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询订单接口(后台)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "orderNum",value = "订单编号",required = false),
            @ApiImplicitParam(name = "orderStatus",value = "订单状态",required = false)
    })
    public Response<BasePageResponse<OrdersDto>> page(int pageNo, int pageSize, String orderNum, String orderStatus){
        log.info("pageNo={},pageSize={},orderNum={},orderStatus={}",pageNo,pageSize,orderNum,orderStatus);
        BasePageResponse<OrdersDto> response = orderService.queryOrderPage(pageNo,pageSize,orderNum,orderStatus);
        return Response.success(response);
    }

    /**
     * 修改订单状态
     * @param orderDto
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改订单状态接口(后台)orderDto必须包含id与orderStatus('待付款','已付款','取消付款'等)")
    public Response<String> update(@RequestBody OrdersDto orderDto){
        log.info(orderDto.toString());
        orderService.updateOrder(orderDto);
        if (orderDto.getOrderStatus().equals("取消付款")){
            //取消付款,商品数量返回原来数值
            orderService.rollBackShopNum(orderDto.getId());
        }
        return Response.success("订单状态修改成功");
    }
}
