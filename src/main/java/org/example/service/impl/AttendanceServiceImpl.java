package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.AttendanceMapper;
import org.example.pojo.Attendance;
import org.example.pojo.AttendanceQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Students;
import org.example.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Override
    public PageResult<Attendance> queryAttendance(AttendanceQueryParam param) {
        //开启分页
        PageHelper.startPage(param.getPageNum(), param.getPageSize());

        //调用Mapper进行动态查询
        Page<Attendance> page = (Page<Attendance>) attendanceMapper.queryAttendance(param);

        //返回分页结果
        return new PageResult<>(
                page.getTotal(),
                page.getPages(),
                page.getResult()
        );
    }

    @Override
    public void signIn(Integer studentId, String status) {
        attendanceMapper.signIn(studentId, status);
    }

    @Override
    public void delete(Integer id) {
        attendanceMapper.delete(id);
    }

    @Override
    public List<Attendance> getByStatus(String status) {
        return attendanceMapper.getByStatus(status);
    }

    @Override
    public void update(Attendance attendance) {
        attendanceMapper.update(attendance);
    }

    @Override
    public List<Students> selectStudentInfoByStudentId(Integer studentId) {
        return attendanceMapper.selectStudentInfoByStudentId(studentId);
    }

    @Override
    public void add(Integer studentId) {
         attendanceMapper.add(studentId);
    }

    @Override
    public List<Attendance> list() {
        return attendanceMapper.list();
    }

    @Autowired
    private AttendanceMapper attendanceMapper;
}
