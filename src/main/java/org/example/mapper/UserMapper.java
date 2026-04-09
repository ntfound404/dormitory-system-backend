package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.User;
import org.example.pojo.UserQueryParam;

import java.util.List;


@Mapper
public interface UserMapper {
    @Select("select * from users where username = #{username}")
    User findByUserName(String username);


    @Insert("insert into users(username,password,created_time,updated_time) " +
            "values (#{username},#{password},now(),now())")
    void add(String username, String password);


    @Update("update users set password = #{md5String},updated_time=now() where id = #{id}")
    void updatePwd(String md5String, Integer id);

    @Select("select * from users")
    List<User> list();

    @Update("update users set nickname = #{nickname},email=#{email},updated_time = now() where id = #{id}")
    void update(User user);

    @Delete("delete from users where id = #{id}")
    void deleteById(Integer userId);

    @Update("update users set role = #{roleId} where id = #{userId}")
    void updateUserRole(Integer userId, Integer roleId);

    List<User> queryByUser(@Param("param") UserQueryParam param);
}
