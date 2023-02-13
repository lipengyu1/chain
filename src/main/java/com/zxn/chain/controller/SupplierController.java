package com.zxn.chain.controller;

import com.zxn.chain.dto.SupplierDto;
import com.zxn.chain.entity.Supplier;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.SupplierServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/supplier")
@Api(tags = "供应商管理相关接口")
public class SupplierController {
    @Autowired
    private SupplierServiceImpl supplierService;

    /**
     * 新增供应商
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增供应商接口(后台)")
    public Response<String> save(@RequestBody SupplierDto supplierDto){
        log.info(supplierDto.toString());
        supplierService.saveSupplier(supplierDto);
        return Response.success("新增供应商成功");
    }
    /**
     * 删除供应商（单选、多选）逻辑删除
     * @param ids
     * @return
     */
    @PutMapping("/del")
    @ApiOperation(value = "删除供应商接口(后台)")
    public Response<String> delete(@RequestParam Long[] ids){
        log.info("ids:{}",ids);
        supplierService.removeSupplier(ids);
        return Response.success("供应商删除成功");
    }

    /**
     * 供应商分页查询
     * @param pageNo
     * @param pageSize
     * @param supplierAddress
     * @param shopCategory
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询供应商接口(后台)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "supplierAddress",value = "供应商名称",required = false),
            @ApiImplicitParam(name = "shopCategory",value = "商品类型",required = false)
    })
    public Response<BasePageResponse<SupplierDto>> page(int pageNo, int pageSize, String supplierAddress,String shopCategory){
        log.info("pageNo={},pageSize={},supplierAddress={},shopCategory={}",pageNo,pageSize,supplierAddress,shopCategory);
        BasePageResponse<SupplierDto> response = supplierService.querySupplierPage(pageNo,pageSize,supplierAddress,shopCategory);
        return Response.success(response);
    }

    /**
     * 修改供应商
     * @param supplierDto
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改供应商接口(后台)")
    public Response<String> update(@RequestBody SupplierDto supplierDto){
        log.info(supplierDto.toString());
        supplierService.updateSupplier(supplierDto);
        return Response.success("供应商修改成功");
    }

    /**
     * id查询供应商（回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "查询供应商接口(id)(后台)")
    public Response<SupplierDto> getById(@PathVariable Long id){
        log.info("根据id查询供应商...");
        SupplierDto supplierDto = supplierService.selectSupplierById(id);
        if (supplierDto != null){
            return Response.success(supplierDto);
        }
        return Response.error("未查询到供应商");
    }
}
