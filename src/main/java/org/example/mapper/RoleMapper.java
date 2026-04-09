package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Roles;

@Mapper
public interface RoleMapper {
    @Select("SELECT * FROM roles WHERE role_name = #{roleName}")
    Roles findByName(String roleName);
}
