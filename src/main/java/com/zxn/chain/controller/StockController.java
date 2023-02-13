package com.zxn.chain.controller;


import com.zxn.chain.dto.StockDto;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.StockServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/stock")
@Api(tags = "库存信息管理相关接口")
public class StockController {

    @Autowired
    private StockServiceImpl stockService;
    /**
     * 新增库存
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增库存接口(后台)")
    public Response<String> save(@RequestBody StockDto stockDto){
        log.info(stockDto.toString());
        stockService.saveStock(stockDto);
        return Response.success("新增库存成功");
    }

    /**
     * 删除库存（单选、多选）逻辑删除
     * @param ids
     * @return
     */
    @PutMapping("/del")
    @ApiOperation(value = "删除库存接口(后台)")
    public Response<String> delete(@RequestParam Long[] ids){
        log.info("ids:{}",ids);
        stockService.removeStock(ids);
        return Response.success("库存删除成功");
    }

    /**
     * 库存分页查询
     * @param pageNo
     * @param pageSize
     * @param shopSupplier
     * @param shopCategory
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询库存接口(后台)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "shopSupplier",value = "供应商",required = false),
            @ApiImplicitParam(name = "shopCategory",value = "类型",required = false)
    })
    public Response<BasePageResponse<StockDto>> page(int pageNo, int pageSize, String shopSupplier, String shopCategory){
        log.info("pageNo={},pageSize={},shopName={},shopCategory={}",pageNo,pageSize,shopSupplier,shopCategory);
        BasePageResponse<StockDto> response = stockService.queryShopPage(pageNo,pageSize,shopSupplier,shopCategory);
        return Response.success(response);
    }

    /**
     * 修改库存
     * @param stockDto
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改库存接口(后台)")
    public Response<String> update(@RequestBody StockDto stockDto){
        log.info(stockDto.toString());
        stockService.updateStock(stockDto);
        return Response.success("库存修改成功");
    }

    /**
     * id查询库存（回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "查询库存接口(id)(后台)")
    public Response<StockDto> getById(@PathVariable Long id){
        log.info("根据id查询库存...");
        StockDto stockDto = stockService.selectStockById(id);
        if (stockDto != null){
            return Response.success(stockDto);
        }
        return Response.error("未查询到库存");
    }

}
