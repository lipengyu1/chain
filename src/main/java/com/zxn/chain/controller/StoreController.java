package com.zxn.chain.controller;

import com.zxn.chain.dto.StoreDto;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.StoreServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/store")
@Api(tags = "门店信息等相关接口")
public class StoreController {

    @Autowired
    private StoreServiceImpl storeService;

    /**
     * 新增门店
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增供应商接口")
    public Response<String> save(@RequestBody StoreDto storeDto){
        log.info(storeDto.toString());
        storeService.saveStore(storeDto);
        return Response.success("新增门店成功");
    }

    /**
     * 删除门店（单选、多选）逻辑删除
     * @param ids
     * @return
     */
    @PutMapping("/del")
    @ApiOperation(value = "删除门店接口")
    public Response<String> delete(@RequestParam Long[] ids){
        log.info("ids:{}",ids);
        storeService.removeStore(ids);
        return Response.success("门店删除成功");
    }

    /**
     * 门店分页查询
     * @param pageNo
     * @param pageSize
     * @param storeType
     * @param storeArea
     * @param storeLeaderName
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询门店接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "storeType",value = "门店名称",required = false),
            @ApiImplicitParam(name = "storeArea",value = "门店区域",required = false),
            @ApiImplicitParam(name = "storeLeaderName",value = "负责人",required = false)
    })
    public Response<BasePageResponse<StoreDto>> page(int pageNo, int pageSize, String storeType, String storeArea,String storeLeaderName){
        log.info("pageNo={},pageSize={},storeType={},storeLeaderName={},",pageNo,pageSize,storeType,storeArea,storeLeaderName);
        BasePageResponse<StoreDto> response = storeService.queryStorePage(pageNo,pageSize,storeType,storeArea,storeLeaderName);
        return Response.success(response);
    }

    /**
     * 修改门店
     * @param storeDto
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改门店接口")
    public Response<String> update(@RequestBody StoreDto storeDto){
        log.info(storeDto.toString());
        storeService.updateStore(storeDto);
        return Response.success("门店修改成功");
    }

    /**
     * id查询门店（回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "查询门店接口(id)")
    public Response<StoreDto> getById(@PathVariable Long id){
        log.info("根据id查询门店...");
        StoreDto storeDto = storeService.selectStoreById(id);
        if (storeDto != null){
            return Response.success(storeDto);
        }
        return Response.error("未查询到供应商");
    }

}
