package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.User;
import org.example.pojo.UserRoles;

import java.util.List;

@Mapper
public interface UserRoleMapper {


    /**
     * 根据用户 ID 查询角色名称列表
     *
     * @param userId 用户 ID
     * @return 角色名称列表
     */
    @Select("""
            SELECT r.role_name 
            FROM roles r 
            INNER JOIN user_roles ur ON r.id = ur.role_id 
            WHERE ur.user_id = #{userId}
            """)
    List<String> findRolesByUserId(@Param("userId") Long userId);

    @Insert("INSERT INTO user_roles (user_id, role_id) VALUES (#{userId}, #{roleId})")
    void insert(@Param("userId") int userId, @Param("roleId") int roleId);

    @Select("SELECT COUNT(1) > 0 FROM user_roles WHERE user_id = #{userId} AND role_id = #{roleId}")
    boolean exists(@Param("userId") int userId, @Param("roleId") int roleId);

    @Update("UPDATE user_roles SET role_id = #{roleId} WHERE user_id = #{userId}")
    void update(Integer userId, Integer roleId);

    @Delete("DELETE FROM user_roles WHERE user_id = #{userId}")
    void deleteByUserId(Integer id);
}

