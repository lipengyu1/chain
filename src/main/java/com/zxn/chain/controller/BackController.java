package com.zxn.chain.controller;

import com.zxn.chain.dto.EmployeeDto;
import com.zxn.chain.entity.StoreManager;
import com.zxn.chain.entity.User;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.EmployeeServiceImpl;
import com.zxn.chain.service.impl.StoreManagerServiceImpl;
import com.zxn.chain.service.impl.UserServiceImpl;
import com.zxn.chain.utils.JwtUtils;
import com.zxn.chain.utils.ValidateCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/back")
@Api(tags = "登录注册等相关接口")
public class BackController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StoreManagerServiceImpl storeManagerService;
    @Autowired
    private EmployeeServiceImpl employeeService;

    /**
     * 通用注册
     * @return
     */
    @PutMapping("/register")
    @ApiOperation(value = "注册接口")
    public Response<String> register(@RequestBody EmployeeDto employeeDto, HttpServletRequest httpServletRequest){
        log.info("员工注册");
        if (employeeDto.getRole().equals("管理员")){
            User user = new User();
            user.setUsername(employeeDto.getUsername());
            user.setPassword(employeeDto.getPassword());
            user.setManagerEmail(employeeDto.getEmail());
            user.setRole(employeeDto.getRole());
            //调用登录查询，判断管理员是否存在
            User user1 =  userService.userLogin(user);
            if (user1 == null){
                User email = userService.getUserEmail(user.getManagerEmail());
                if (email == null){
                    userService.userRegister(user);
                    return Response.success("注册成功");
                }else {
                    return Response.error("该邮箱已注册");
                }
            }else {
                return Response.error("该账号已注册");
            }
        }else if (employeeDto.getRole().equals("员工")){
            StoreManager storeManager = new StoreManager();
            storeManager.setUsername(employeeDto.getUsername());
            storeManager.setPassword(employeeDto.getPassword());
            storeManager.setEmail(employeeDto.getEmail());
            storeManager.setRole(employeeDto.getRole());
            //调用登录查询，判断用户是否存在
            StoreManager storeManager1 =  storeManagerService.storeManagerLogin(storeManager);
            if (storeManager1 == null){
                StoreManager email = storeManagerService.getEmpEmail(storeManager.getEmail());
                if (email == null){
                    storeManagerService.managerRegister(storeManager);
                    return Response.success("注册成功");
                }else {
                    return Response.error("该邮箱已注册");
                }
            }else {
                return Response.error("该账号已注册");
            }
        }else{
            return Response.error("角色错误");
        }
    }

    /**
     * 登录
     * @param employeeDto
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public Response login(@RequestBody EmployeeDto employeeDto, HttpServletRequest httpServletRequest) {
        String password = employeeDto.getPassword();

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        String role = employeeDto.getRole();

        if (role.equals("员工")){
            StoreManager storeManager = new StoreManager();
            storeManager.setUsername(employeeDto.getUsername());
            storeManager.setPassword(password);
            storeManager.setRole(role);
            storeManager.setEmail(employeeDto.getEmail());
            StoreManager storeMan = storeManagerService.storeManagerLogin(storeManager);
            if (storeMan == null) {
                return Response.error("登录失败，该门店员工不存在");
            }
            if (!(storeMan.getPassword().equals(password))) {
                return Response.error("登录失败，密码错误");
            }
            if (storeMan.getStatus() == 0) {
                return Response.error("账号已禁用");
            }

            //准备存放在IWT中的自定义数据
            Map<String, Object> info = new HashMap<>();
            //生成token
            String token = JwtUtils.sign(storeMan.getId(), info);
            storeMan.setToken(token);

            return Response.success(storeMan);
        }else if (role.equals("管理员")){
            User user = new User();
            user.setRole(role);
            user.setUsername(employeeDto.getUsername());
            user.setPassword(employeeDto.getPassword());
            user.setManagerEmail(employeeDto.getEmail());
            User user1 = userService.userLogin(user);
            if (user1 == null) {
                return Response.error("登录失败，该管理员不存在");
            }
            if (!(user1.getPassword().equals(password))) {
                return Response.error("登录失败，密码错误");
            }
            if (user1.getStatus() == 0) {
                return Response.error("账号已禁用");
            }

            //准备存放在IWT中的自定义数据
            Map<String, Object> info = new HashMap<>();
            //生成token
            String token = JwtUtils.sign(user1.getId(), info);
            user1.setToken(token);

            return Response.success(user1);
        }else {
            return Response.error("该角色不存在");
        }
    }

    /**
     * 发送验证码
     * @param employeeDto
     * @return
     */
    @PostMapping("/sendMsg")
    @ApiOperation(value = "发送验证码接口")
    public Response<String> sendMsg(@RequestBody EmployeeDto employeeDto){
        String email = employeeDto.getEmail();
        String subject = "连锁店管理系统";
        if (StringUtils.isNotEmpty(email)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            String text = "【连锁店管理系统】您好，您的验证码为：" + code + "，有效期5分钟，请尽快登录";
            log.info("验证码为：" + code);
            try {
                employeeService.sendMsg(email,subject,text);
            } catch (Exception e) {
                return Response.error("邮箱错误，请重新输入");
            }
            redisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);
            return Response.success("验证码发送成功");
        }
        return Response.error("验证码发送异常，请重新发送");
    }

    /**
     * 找回密码
     * @param map
     * @return
     */
    @PutMapping("/find")
    @ApiOperation(value = "找回密码接口")
    public Response<String> find(@RequestBody Map map) {
        log.info("找回密码", map);
        String role = map.get("role").toString();
        String email = map.get("email").toString();
        String code = map.get("code").toString();
        String newPasswd = map.get("password").toString();
        newPasswd =  DigestUtils.md5DigestAsHex(newPasswd.getBytes());
        Object sessionCode = redisTemplate.opsForValue().get(email);
        if (sessionCode != null && sessionCode.equals(code)) {
            if (role.equals("管理员")){
                userService.setNewPasswd(email,newPasswd);
                redisTemplate.delete(email);
                return Response.success("找回密码成功");
            }else if (role.equals("员工")){
                storeManagerService.setNewPasswd(email,newPasswd);
                redisTemplate.delete(email);
                return Response.success("找回密码成功");
            }else {
                return Response.error("角色错误");
            }
        }
        return Response.error("验证码错误");
    }
}
