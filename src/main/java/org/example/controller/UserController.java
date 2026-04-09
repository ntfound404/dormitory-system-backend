package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.pojo.UserQueryParam;
import org.example.service.UserRoleService;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password) {
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


    @PostMapping("/login")
    public Result<Map<String, Object>> login(
            @RequestParam @Pattern(regexp = "^\\S{5,16}$", message = "用户名必须是5到16个非空格字符") String username,
            @RequestParam @Pattern(regexp = "^\\S{5,16}$", message = "密码必须是5到16个非空格字符") String password) {
        // 1. 查找用户
        User loginUser = userService.findByUserName(username);
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        // 2. 校验密码
        if (!Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            return Result.error("密码错误");
        }
        // 3. 获取用户角色
        List<String> roles = userRoleService.findRolesByUserId((long) loginUser.getId());
        if (roles.isEmpty()) {
            return Result.error("用户没有分配角色");
        }
        // 4. 登录成功，生成 JWT Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        claims.put("username", loginUser.getUsername());
        claims.put("roles", roles); // 将角色列表添加到 JWT 中
        String token = JwtUtil.genToken(claims);
        // 5. 存储 token 到 Redis，设置过期时间（如30分钟）
        stringRedisTemplate.opsForValue().set(token, token, 30, TimeUnit.MINUTES);
        // 6. 返回响应信息
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("roles", roles);
        String message = "登录成功，角色: " + String.join(", ", roles);
        return Result.success(response, message);
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token){
        Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }



    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params,@RequestHeader("Authorization")String token){
        //1.参数校验
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }

        //原密码是否正确
        //调用userService根据用户名拿到原密码，在和old_Pwd比较
        Map<String, Object>map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User LoginUser = userService.findByUserName(username);
        if (!LoginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }

        //newPwd和rePwd是否一致
        if (!rePwd.equals((newPwd))){
            return Result.error("两次密码不一致");
        }

        //2.调用service完成密码更新
        userService.updatePwd(newPwd);
        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }

    @PostMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){
        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success("退出成功");
    }

    @GetMapping("/list")
    public Result list(){
        List<User> users = userService.list();
        return Result.success(users);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PostMapping("/query")
    public Result<PageResult<User>> query(@RequestBody UserQueryParam param) {
        PageResult<User> pageResult = userService.queryUser(param);
        System.out.println(param);
        return Result.success(pageResult);
    }

}
