package org.example.service;


import org.example.pojo.PageResult;
import org.example.pojo.User;
import org.example.pojo.UserQueryParam;

import java.util.List;


public interface UserService {

    User findByUserName(String userName);

    void register(String userName, String password);


    void updatePwd(String newPwd);

    List<User> list();

    void update(User user);


    void deleteById(Integer id);


    void updateUserRole(Integer userId, Integer roleId);

    PageResult<User> queryUser(UserQueryParam param);
}
