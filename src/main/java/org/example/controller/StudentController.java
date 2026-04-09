package org.example.controller;


import org.example.pojo.*;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.pojo.StudentQueryParam;

import java.util.List;

@RequestMapping("/admin/student")
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;


    @PostMapping("/add")
    public Result add(@RequestBody Students students) {
        studentService.add(students);

        Integer studentId = students.getId();

        attendanceService.add(studentId);

        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Students students) {
        studentService.update(students);
        return Result.success();
    }

    @PostMapping
    public Result delete(@RequestParam Integer id) {
        studentService.deleteById(id);
        attendanceService.delete(id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list() {
        List<Students> students = studentService.list();
        return Result.success(students);
    }

    // 批量删除学生
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        studentService.deleteBatch(ids);
        return Result.success();
    }

    //通过dormId查询宿舍楼
    @GetMapping("/dorm")
    public Result listByDormId(@RequestParam Integer dormId) {
        List<Dorms> dorms = dormService.listByDormId(dormId);
        return Result.success(dorms);
    }

    //通过dormId查询寝室号
    @GetMapping("/room")
    public Result listByRoomId(@RequestParam Integer dormId) {
        List<DormRooms> rooms = dormRoomService.listByRoomId(dormId);
        return Result.success(rooms);
    }

    //查询未绑定学生的用户列表
    @GetMapping("/unbound")
    public Result listUnbound() {
        List<User> users = studentUserService.listUnbound();
        return Result.success(users);
    }

    @PostMapping("/selectStudentUser")
    public Result selectStudentUser(@RequestParam Integer userId, @RequestParam Integer studentId) {
        studentUserService.selectStudentUser(userId, studentId);
        return Result.success();
    }

    @PostMapping("/checkStudentUser")
    public Result checkStudentUser(@RequestParam Integer studentId) {
        List<User> user = studentUserService.checkStudentUser(studentId);
        return Result.success(user);
    }

    @PostMapping("/updateStudentUser")
    public Result updateStudentUser(@RequestParam(required = false) String userId, @RequestParam Integer studentId) {
        if (userId == null || userId.equals("null")) {
            // 解除绑定的逻辑
            studentUserService.unbindStudentUser(studentId);
        } else {
            // 将字符串转换为Integer
            try {
                Integer userIdInt = Integer.parseInt(userId);
                studentUserService.updateStudentUser(userIdInt, studentId);
            } catch (NumberFormatException e) {
                return Result.error("用户ID格式不正确");
            }
        }
        return Result.success();
    }

    @GetMapping("/checkUserBound")
    public Result<Integer> checkUserBound(
            @RequestParam Integer userId,
            @RequestParam(required = false) Integer currentStudentId
    ) {
        Integer boundStudentId = studentUserService.checkUserBound(userId);

        // 如果当前用户已绑定，且与 currentStudentId 不同，则返回绑定信息
        if (boundStudentId != null && !boundStudentId.equals(currentStudentId)) {
            return Result.success(boundStudentId);
        }

        return Result.success(null); // 未绑定任何学生，或者绑定的是当前学生
    }

    /**
     * 动态查询学生信息
     *
     * @param param 查询参数
     * @return 分页查询结果
     */
    @PostMapping("/query")
    public Result<PageResult<Students>> query(@RequestBody StudentQueryParam param) {
        PageResult<Students> pageResult = studentService.queryStudents(param);
        return Result.success(pageResult);
    }

    @Autowired
    private StudentUserService studentUserService;
    @Autowired
    private DormRoomService dormRoomService;
    @Autowired
    private DormService dormService;
    @Autowired
    private AttendanceService attendanceService;

}
