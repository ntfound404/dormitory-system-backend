package org.example.service.impl;

import org.example.mapper.RoleMapper;
import org.example.mapper.UserRoleMapper;
import org.example.pojo.Roles;
import org.example.service.UserRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Override
    public void deleteUserRolesByUserId(Integer id) {
        userRoleMapper.deleteByUserId(id);
    }

    @Override
    public void update(Integer userId, Integer roleId) {
        userRoleMapper.update(userId, roleId);
    }

    @Override
    public void assignRole(int userId, String roleName) {
        // 根据角色名称查询角色 ID
        Roles role = roleMapper.findByName(roleName);
        if (role == null) {
            throw new RuntimeException("角色不存在: " + roleName);
        }

        // 检查用户是否已经拥有该角色
        if (userRoleMapper.exists(userId, role.getId())) {
            throw new RuntimeException("用户已拥有该角色");
        }

        // 分配角色

        userRoleMapper.insert(userId, role.getId());
    }

    @Override
    public List<String> findRolesByUserId(Long userId) {
        // 调用 DAO 层方法查询角色信息
        List<String> roles = userRoleMapper.findRolesByUserId(userId);

        // 检查是否为空并返回结果
        if (roles == null || roles.isEmpty()) {
            throw new RuntimeException("用户未分配任何角色");
        }
        return roles;
    }

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

}
