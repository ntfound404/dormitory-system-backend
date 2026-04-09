package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.UserMapper;
import org.example.pojo.PageResult;
import org.example.pojo.User;
import org.example.pojo.UserQueryParam;
import org.example.service.UserService;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public PageResult<User> queryUser(UserQueryParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        Page<User> page = (Page<User>) userMapper.queryByUser(param);
        return new PageResult<>(
                page.getTotal(),
                page.getPages(),
                page.getResult()
        );
    }

    @Override
    public void updateUserRole(Integer userId, Integer roleId) {
        userMapper.updateUserRole(userId,roleId);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void update(User user) {
        user.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
        userMapper.update(user);
    }

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);
        //添加
        userMapper.add(username,md5String);
    }

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findByUserName(String userName) {
        User u = userMapper.findByUserName(userName);
        return u;
    }


}
