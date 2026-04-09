package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Attendance;
import org.example.pojo.AttendanceQueryParam;
import org.example.pojo.StudentQueryParam;
import org.example.pojo.Students;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    @Select("select * from attendance")
    List<Attendance> list();

    @Insert("insert into attendance(student_id,date) values (#{id},NOW())")
    void add(Integer studentId);

    @Select("select * from students where id = #{studentId}")
    List<Students> selectStudentInfoByStudentId(Integer studentId);

    @Update("update attendance set status = #{status}, remarks = #{remarks},date = NOW() where id = #{id}")
    void update(Attendance attendance);

    @Select("select * from attendance where status = #{status}")
    List<Attendance> getByStatus(@Param("status") String status);

    @Delete("DELETE FROM attendance WHERE student_id = #{id}")
    void delete(Integer id);

    @Update("UPDATE attendance SET status = #{status} WHERE student_id = #{studentId}")
    void signIn(Integer studentId, String status);

    List<Attendance> queryAttendance(@Param("param") AttendanceQueryParam param);
}
