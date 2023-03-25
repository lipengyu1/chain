package com.zxn.chain.service;

import com.zxn.chain.dto.UserDto;
import com.zxn.chain.entity.StoreManager;
import com.zxn.chain.entity.User;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;

import java.util.List;

public interface UserService {
    UserDto selectUserById(Long id);
    BasePageResponse<UserDto> queryUserPage(int pageNo, int pageSize, String name);

    void updateUser(User user);

    void removeUser(Long[] ids);

    User userLogin(User user);

    void setNewPasswd(String email, String newPasswd);

    User getUserEmail(String managerEmail);

    void userRegister(User user);

    Response<String> addUser(User user);
}
