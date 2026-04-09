package org.example.controller;


import org.example.pojo.*;
import org.example.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/list")
    public Result list() {
        List<Attendance> attendance = attendanceService.list();
        return Result.success(attendance);
    }

    //通过studentId查询学生信息
    @GetMapping("/studentId")
    public Result selectStudentInfoByStudentId(@RequestParam Integer studentId) {
        List<Students> students = attendanceService.selectStudentInfoByStudentId(studentId);
        return Result.success(students);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Attendance attendance) {
        attendanceService.update(attendance);
        return Result.success();
    }

    @GetMapping("/status")
    public Result getByStatus(@RequestParam String status) {
        List<Attendance> attendance = attendanceService.getByStatus(status);
        return Result.success(attendance);
    }

    @PostMapping
    public Result delete(@RequestParam Integer id){
        attendanceService.delete(id);
        return Result.success();
    }

    @PostMapping("/query")
    public Result<PageResult<Attendance>> query(@RequestBody AttendanceQueryParam param){
        PageResult<Attendance> pageResult = attendanceService.queryAttendance(param);
        System.out.println(param);
        return Result.success(pageResult);
    }

}
