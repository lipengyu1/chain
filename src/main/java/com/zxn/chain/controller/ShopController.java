package com.zxn.chain.controller;

import com.zxn.chain.dto.ShopDto;
import com.zxn.chain.dto.SupplierDto;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.ShopServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/shop")
@Api(tags = "商品等相关接口")
public class ShopController {
    @Autowired
    private ShopServiceImpl shopService;
    /**
     * 新增商品
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增商品接口(后台)")
    public Response<String> save(@RequestBody ShopDto shopDto){
        log.info(shopDto.toString());
        shopService.saveShop(shopDto);
        return Response.success("新增商品成功");
    }
    /**
     * 删除商品（单选、多选）逻辑删除
     * @param ids
     * @return
     */
    @PutMapping("/del")
    @ApiOperation(value = "删除商品接口(后台)")
    public Response<String> delete(@RequestParam Long[] ids){
        log.info("ids:{}",ids);
        shopService.removeShop(ids);
        return Response.success("商品删除成功");
    }

    /**
     * 商品分页查询
     * @param pageNo
     * @param pageSize
     * @param shopName
     * @param category
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询商品接口(前后台)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "shopName",value = "商品名称",required = false),
            @ApiImplicitParam(name = "category",value = "类型",required = false)
    })
    public Response<BasePageResponse<ShopDto>> page(int pageNo, int pageSize, String shopName, String category){
        log.info("pageNo={},pageSize={},shopName={},category={}",pageNo,pageSize,shopName,category);
        BasePageResponse<ShopDto> response = shopService.queryShopPage(pageNo,pageSize,shopName,category);
        return Response.success(response);
    }

    /**
     * 修改商品
     * @param shopDto
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改商品接口(后台)")
    public Response<String> update(@RequestBody ShopDto shopDto){
        log.info(shopDto.toString());
        shopService.updateShop(shopDto);
        return Response.success("商品修改成功");
    }

    /**
     * id查询商品（回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "查询商品接口(id)(后台)")
    public Response<ShopDto> getById(@PathVariable Long id){
        log.info("根据id查询商品...");
        ShopDto shopDto = shopService.selectShopById(id);
         return Response.success(shopDto);
    }
}
