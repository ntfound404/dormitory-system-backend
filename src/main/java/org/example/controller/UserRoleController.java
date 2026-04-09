package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserRoleService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping("/super-admin/user-role")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;


    @PostMapping("/updateRole")
    public Result update(@RequestParam Integer userId, @RequestParam Integer roleId) {
        try {
            userRoleService.update(userId, roleId);

            // 同步更新 users 表的 role 字段
            userService.updateUserRole(userId, roleId);

            return Result.success("用户角色更新成功");

        } catch (Exception e) {
            return Result.error("用户角色更新失败");
        }
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PostMapping("/add")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户
        User u = userService.findByUserName(username);
        if (u == null) {
            //没有占用
            //注册
            userService.register(username, password);

            User newUser = userService.findByUserName(username);
            if(newUser != null) {
                userRoleService.assignRole(newUser.getId(),"student");
            }

            return Result.success();
        } else {
            //占用
            return Result.error("用户名已被占用");
        }
    }

    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        // 先删除 user_roles 表中的记录
        userRoleService.deleteUserRolesByUserId(id);

        // 删除用户
        userService.deleteById(id);

        return Result.success();
    }

}
