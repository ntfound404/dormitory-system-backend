package org.example.mapper;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.*;
import org.example.pojo.StudentQueryParam;
import org.example.pojo.Students;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Insert("insert into students(student_id, name, gender, phone, `group`,  major, enrollment_year, dorm_id,room_id, created_time, updated_time) " +
            "values(#{studentId}, #{name}, #{gender}, #{phone},#{group}, #{major}, #{enrollmentYear}, #{dormId},#{roomId}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(Students students);

    @Update("update students set student_id = #{studentId}, name = #{name}, gender = #{gender}, `group` = #{group}, phone = #{phone}, major = #{major}, " +
            "enrollment_year = #{enrollmentYear}, dorm_id = #{dormId},room_id = #{roomId},updated_time = now() where id = #{id}")
    void update(Students students);

    @Delete("delete from students where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from students")
    List<Students> list();

    @Delete("<script>"
            + "DELETE FROM students WHERE id IN "
            + "<foreach collection='ids' item='id' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    void deleteBatch(List<Integer> ids);

    @Select("SELECT * FROM students WHERE room_id = #{id}")
    List<Students> getStudentList(Integer id);

    @Select("SELECT * FROM students WHERE id = #{studentId}")
    Students getById(Integer studentId);

    List<Students> queryByCondition(@Param("param") StudentQueryParam param);
}
