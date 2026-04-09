package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Students;
import org.example.pojo.User;

import java.util.List;


@Mapper
public interface StudentUserMapper {

    @Select("SELECT * FROM students WHERE id = (SELECT student_id FROM student_users " +
            "WHERE user_id = #{userId})")
    Students getStudentInfoById(Integer userId);

    @Insert("INSERT INTO student_users (user_id, student_id) VALUES (#{userId}, #{studentId})")
    void selectStudentUser(Integer userId, Integer studentId);

    @Select("SELECT s.* FROM students s INNER JOIN student_users su ON s.id = su.student_id " +
            "WHERE su.user_id = #{userId}")
    Students checkUserStudent(Integer userId);

    @Select("SELECT * FROM users WHERE id NOT IN (SELECT user_id FROM student_users)")
    List<User> listUnbound();

    @Select("SELECT u.* FROM users u INNER JOIN student_users su ON u.id = su.user_id WHERE su.student_id = #{studentId}")
    List<User> checkStudentUser(Integer studentId);

    @Update("UPDATE student_users SET user_id = #{userId} WHERE student_id = #{studentId}")
    void updateStudentUser(Integer userId, Integer studentId);

    @Select("SELECT student_id FROM student_users WHERE user_id = #{userId} LIMIT 1")
    Integer findStudentIdByUserId(@Param("userId") Integer userId);

    @Delete("DELETE FROM student_users WHERE student_id = #{studentId}")
    void unbindStudentUser(Integer studentId);
}
