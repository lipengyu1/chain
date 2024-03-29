package com.zxn.chain.service.impl;

import com.zxn.chain.dao.UserDao;
import com.zxn.chain.dto.UserDto;
import com.zxn.chain.entity.StoreManager;
import com.zxn.chain.entity.User;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.SnowService;
import com.zxn.chain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    SnowService snowService = new SnowService(1, 1);

    @Override
    public UserDto selectUserById(Long id) {
        return userDao.selectUserById(id);
    }

    @Override
    public BasePageResponse<UserDto> queryUserPage(int pageNo, int pageSize, String managerName) {
        int pageNo1 = pageSize * (pageNo - 1);
        List<UserDto> queryList = userDao.queryUserPage(pageNo1,pageSize,managerName);
        ArrayList<UserDto> arrayList = new ArrayList<>(queryList);
        int totalCount = userDao.queryUserCount(pageNo1,pageSize,managerName);
        BasePageResponse<UserDto> basePageResponse = new BasePageResponse<>();
        basePageResponse.setPageNo(pageNo);
        basePageResponse.setPageSize(pageSize);
        basePageResponse.setTotalPage((int)Math.ceil((float)totalCount/pageSize));
        basePageResponse.setResultList(arrayList);
        basePageResponse.setTotalCount(totalCount);
        return basePageResponse;
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public User userLogin(User user) {
        return userDao.userLogin(user);

    }

    @Override
    public void removeUser(Long[] ids) {
        for (int i = 0; i <ids.length; i++) {
            Long id = ids[i];
            userDao.removeUser(id);
        }
    }

    @Override
    public void setNewPasswd(String email, String newPasswd) {
        userDao.setNewPasswd(email,newPasswd);
    }



    @Override
    public User getUserEmail(String managerEmail) {
        return userDao.getUserEmail(managerEmail);
    }

    @Override
    public void userRegister(User user) {
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        user.setId(snowService.getId());
        userDao.userRegister(user);
    }

    @Override
    public Response<String> addUser(User user) {
        User user1 = userDao.userLogin(user);
        if (user1 == null){
            User email = userDao.getUserEmail(user.getManagerEmail());
            if (email == null){
                String password = user.getPassword();
                password = DigestUtils.md5DigestAsHex(password.getBytes());
                user.setPassword(password);
                user.setId(snowService.getId());
                user.setRole("管理员");
                userDao.add(user);
                return Response.success("添加成功");
            }else{
                return Response.error("该邮箱已存在");
            }
        }else {
            return Response.error("该账号已存在");
        }
    }
}
