package org.example.service;

import org.example.pojo.Result;
import org.example.pojo.User;

import java.util.List;

public interface UserRoleService {



    List<String> findRolesByUserId(Long userId);

    void assignRole(int userId, String roleName);

    void update(Integer userId, Integer roleId);

    void deleteUserRolesByUserId(Integer id);
}
