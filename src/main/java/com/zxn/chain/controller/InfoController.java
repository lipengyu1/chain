package com.zxn.chain.controller;

import com.zxn.chain.dto.WarnDto;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.StockServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/info")
@Api(tags = "资讯相关接口")
public class InfoController {

    @Autowired
    private StockServiceImpl stockService;

    @GetMapping("/warn")
    @ApiOperation(value = "库存预警接口")
    public Response<ArrayList<WarnDto>> warning(){
        ArrayList<WarnDto> shopName =  stockService.stockWarn();
        return Response.success(shopName);
    }

    @GetMapping("/sellnum")
    @ApiOperation(value = "销售数量接口")
    public Response<ArrayList<WarnDto>> sellNum(){
        ArrayList<WarnDto> sellNum = stockService.getShopSellNum();
        return Response.success(sellNum);
    }

}
