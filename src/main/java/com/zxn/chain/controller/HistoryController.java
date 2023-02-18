package com.zxn.chain.controller;


import com.zxn.chain.dto.ShopUserHistoryDto;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.HistoryServiceImpl;
import com.zxn.chain.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@Slf4j
@RestController
@RequestMapping("/history")
@Api(tags = "历史记录接口")
public class HistoryController {
    @Autowired
    private HistoryServiceImpl historyService;

//    删除历史浏览记录
    @DeleteMapping("/delhistory")
    @ApiOperation(value = "用户删除历史记录(前台)")
    public Response<String> delHistory(@RequestParam Long shopId,Long memberNum){
        historyService.delHistory(memberNum,shopId);
        return Response.success("删除成功");
    }

//    查询历史浏览记录（根据redis中的shopId查询商品信息）
    @GetMapping("/gethistory")
    @ApiOperation(value = "用户查询历史记录(前台)")
    public Response<ArrayList<ShopUserHistoryDto>> queryHistory(@RequestParam Long memberNum){
    ArrayList<ShopUserHistoryDto> arrayList = historyService.queryHistory(memberNum);
    return Response.success(arrayList);
    }
}
