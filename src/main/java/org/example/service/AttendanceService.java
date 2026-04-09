package org.example.service;

import org.example.pojo.Attendance;
import org.example.pojo.AttendanceQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Students;

import java.util.List;

public interface AttendanceService {
    List<Attendance> list();

    void add(Integer studentId);


    List<Students> selectStudentInfoByStudentId(Integer studentId);

    void update(Attendance attendance);


    List<Attendance> getByStatus(String status);

    void delete(Integer id);

    void signIn(Integer studentId, String status);

    PageResult<Attendance> queryAttendance(AttendanceQueryParam param);
}
