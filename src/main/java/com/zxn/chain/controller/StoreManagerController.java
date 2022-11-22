package com.zxn.chain.controller;

import com.zxn.chain.dto.StoreManagerDto;
import com.zxn.chain.entity.StoreManager;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.StoreManagerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/storemanager")
@Api(tags = "门店员工管理相关接口")
public class StoreManagerController {

    @Autowired
    private StoreManagerServiceImpl storeManagerService;

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工退出接口")
    public Response<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("storeManager");
        return Response.success("退出成功");
    }

    /**
     * 门店员工分页查询
     * @param pageNo
     * @param pageSize
     * @param storeEmployeeName
     * @param storeSaleStatus
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询员工接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "storeEmployeeName",value = "姓名",required = false),
            @ApiImplicitParam(name = "storeSaleStatus",value = "销售状况",required = false)
    })
    public Response<BasePageResponse<StoreManagerDto>> page(int pageNo, int pageSize,
                                                            String storeEmployeeName,String storeSaleStatus){
        log.info("pageNo={},pageSize={},storeEmployeeName={},storeSaleStatus={}",pageNo,pageSize,storeEmployeeName,storeSaleStatus);
        BasePageResponse<StoreManagerDto> response = storeManagerService.queryStoreManagerPage(pageNo,pageSize,storeEmployeeName,storeSaleStatus);
        return Response.success(response);
    }

    /**
     * 删除员工（单选、多选）逻辑删除
     * @param ids
     * @return
     */
    @PutMapping("/del")
    @ApiOperation(value = "员工删除接口")
    public Response<String> delete(@RequestParam Long[] ids){
        log.info("ids:{}",ids);
        storeManagerService.removeStoreManager(ids);
        return Response.success("门店员工删除成功");
    }

    /**
     * 员工信息修改
     * @param storeManager
     * @return
     */
    @PutMapping
    @ApiOperation(value = "员工信息修改接口")
    public Response<String> update(@RequestBody StoreManager storeManager){
        log.info(storeManager.toString());
        storeManagerService.updateStoreManager(storeManager);
        return Response.success("修改成功");
    }

    /**
     * id查询门店员工（回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "员工查询接口(id)")
    public Response<StoreManager> getById(@PathVariable Long id){
        log.info("根据id查询门店员工...");
        StoreManager storeManager = storeManagerService.selectStoreManagerById(id);
        if (storeManager != null){
            return Response.success(storeManager);
        }
        return Response.error("未查询到员工");
    }

}
