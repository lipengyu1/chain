package com.zxn.chain.dao;

import com.zxn.chain.dto.UserDto;
import com.zxn.chain.entity.User;

import java.util.List;

public interface UserDao {
    UserDto selectUserById(Long id);

    List<UserDto> queryUserPage(int pageNo, int pageSize, String managerName);

    int queryUserCount(int pageNo, int pageSize, String managerName);

    void updateUser(User user);

    void removeUser(Long id);

    User userLogin(User user);

    void setNewPasswd(String email, String newPasswd);

    User getUserEmail(String managerEmail);

    void userRegister(User user);

    void add(User user);
}
