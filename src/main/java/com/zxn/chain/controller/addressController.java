package com.zxn.chain.controller;


import com.zxn.chain.entity.Address;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.AddressServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/address")
@Api(tags = "用户地址管理等相关接口")
public class addressController {

    @Autowired
    private AddressServiceImpl addressService;
    /**
     * 新增地址
     * @return
     */
//    @CacheEvict(value = "addressCache",allEntries = true)
    @PostMapping
    @ApiOperation(value = "新增地址接口(前台)")
    public Response<String> save(@RequestBody Address address){
        log.info(address.toString());
        addressService.saveAddress(address);
        return Response.success("新增地址成功");
    }
    /**
     * 删除地址（单选、多选）逻辑删除
     * @param ids
     * @return
     */
//    @CacheEvict(value = "addressCache",allEntries = true)
    @PutMapping("/del")
    @ApiOperation(value = "删除地址接口(前台)")
    public Response<String> delete(@RequestParam Long[] ids){
        log.info("ids:{}",ids);
        addressService.removeAddress(ids);
        return Response.success("地址删除成功");
    }
    /**
     * 地址分页查询
     * @param pageNo
     * @param pageSize
     * @param memberNum
     * @return
     */
//    @Cacheable(value = "addressCache",key = "#memberNum+'_'+'address'")
    @GetMapping("/page")
    @ApiOperation(value = "分页查询地址接口(前台)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "memberNum",value = "会员编号",required = true),
    })
    public Response<BasePageResponse<Address>> page(int pageNo, int pageSize, Long memberNum){
        log.info("pageNo={},pageSize={},shopName={},category={}",pageNo,pageSize,memberNum);
        BasePageResponse<Address> response = addressService.queryAddressPage(pageNo,pageSize,memberNum);
        return Response.success(response);
    }

    /**
     * 修改地址
     * @param address
     * @return
     */
//    @CacheEvict(value = "addressCache",allEntries = true)
    @PutMapping
    @ApiOperation(value = "修改地址接口(前台)")
    public Response<String> update(@RequestBody Address address){
        log.info(address.toString());
        addressService.updateAddress(address);
        return Response.success("地址修改成功");
    }
    /**
     * id查询地址（回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "查询地址接口(id)(前台)")
    public Response<Address> getById(@PathVariable Long id){
        log.info("根据id查询地址...");
        Address address = addressService.selectAddressById(id);
        return Response.success(address);
    }
}
