package com.zxn.chain.controller;

import com.zxn.chain.dto.UserDto;
import com.zxn.chain.entity.StoreManager;
import com.zxn.chain.entity.User;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.UserServiceImpl;
import com.zxn.chain.utils.ValidateCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "管理员相关接口")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "管理员退出接口")
    public Response<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return Response.success("退出成功");
    }

    /**
     * id查询员工信息（回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "管理员查询接口(id)(后台)")
    public Response<UserDto> getById(@PathVariable Long id){
        log.info("根据id查询员工信息...");
        UserDto user = userService.selectUserById(id);
        if (user != null){
            return Response.success(user);
        }
        return Response.error("未查询到员工信息");
    }


    /**
     * 信息分页查询与条件（name）查询
     * @param pageNo
     * @param pageSize
     * @param managerName
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询管理员接口(后台)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "managerName",value = "姓名",required = false),
    })
    public Response<BasePageResponse<UserDto>> page(int pageNo, int pageSize, String managerName){
        log.info("pageNo={},pageSize={},name={}",pageNo,pageSize,managerName);
        BasePageResponse<UserDto> response = userService.queryUserPage(pageNo,pageSize,managerName);
        return Response.success(response);
    }

    /**
     * 修改信息
     * @param user
     * @return
     */
    @PutMapping
    @ApiOperation(value = "管理员信息修改接口(后台)")
    public Response<String> update(@RequestBody User user){
        log.info(user.toString());
        userService.updateUser(user);
        return Response.success("员工信息修改成功");
    }

    /**
     * 删除（单选、多选）逻辑删除
     * /employee/del?ids=1,2
     * @param ids
     * @return
     */
    @PutMapping("/del")
    @ApiOperation(value = "管理员删除接口(后台)")
    public Response<String> delete(@RequestParam Long[] ids){
        log.info("ids:{}",ids);
        userService.removeUser(ids);
        return Response.success("员工删除成功");
    }
}
