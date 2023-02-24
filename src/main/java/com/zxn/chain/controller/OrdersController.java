package com.zxn.chain.controller;


import com.zxn.chain.dto.OrdersDto;
import com.zxn.chain.dto.ShopDto;
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
     * @param orderStatus
     * @param memberNum
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询订单接口(前后台)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "orderStatus",value = "订单状态",required = false),
            @ApiImplicitParam(name = "memberNum",value = "会员编号，前台使用",required = false)
    })
    public Response<BasePageResponse<OrdersDto>> page(int pageNo, int pageSize, String orderStatus,Long memberNum){
        log.info("pageNo={},pageSize={},orderNum={},orderStatus={},memberNum={}",pageNo,pageSize,orderStatus,memberNum);
        BasePageResponse<OrdersDto> response = orderService.queryOrderPage(pageNo,pageSize,orderStatus,memberNum);
        return Response.success(response);
    }

    /**
     * 选择订单地址
     * @param orderId
     * @param addressId
     * @return
     */
    @PutMapping("/addaddress")
    @ApiOperation(value = "选择订单地址")
    public Response<String> update(@RequestParam Long orderId,Long addressId){
        orderService.addOrderAddress(orderId,addressId);
        return Response.success("地址选择成功");
    }

    /**
     * 修改订单状态
     * @param orderDto
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改订单状态接口(前后台)orderDto必须包含id与orderStatus('待付款','已付款','取消付款'等)")
    public Response<String> update(@RequestBody OrdersDto orderDto){
        log.info(orderDto.toString());
        orderService.updateOrder(orderDto);
        if (orderDto.getOrderStatus().equals("取消付款")){
            //取消付款,商品数量返回原来数值
            orderService.rollBackShopNum(orderDto.getId());
        }
        return Response.success("订单状态修改成功");
    }

    /**
     * id查询订单（回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "查询订单接口(id)(后台)")
    public Response<OrdersDto> getById(@PathVariable Long id){
        log.info("根据id查询商品...");
        OrdersDto ordersDto = orderService.selectOrderById(id);
        return Response.success(ordersDto);
    }
}
