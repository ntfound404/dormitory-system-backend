package org.example.controller;

import org.example.pojo.*;
import org.example.service.*;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/studentUser")
public class StudentUserController {
    @Autowired
    private StudentUserService studentUserService;
    @Autowired
    private DormRoomService dormRoomService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private DormService dormService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private NoticesService noticesService;
    //通过用户id查询学生信息(已绑定）
    @GetMapping("/getStudentInfo")
    public Result getStudentInfoById(@RequestParam Integer userId) {
        Students student = studentUserService.getStudentInfoById(userId);
        return Result.success(student);
    }

    @GetMapping("/room")
    public Result listByRoomId(@RequestParam Integer roomId) {
        List<DormRooms> rooms = dormRoomService.listByRoomId(roomId);
        return Result.success(rooms);
    }

    //通过宿舍楼id查询对应寝室
    @GetMapping("/dormRoom")
    public Result listByDormId(@RequestParam Integer dormId){
        List<DormRooms> rooms = dormRoomService.listByDormId(dormId);
        return Result.success(rooms);
    }

    @PostMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){
        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success("退出成功");
    }

    @PutMapping("/updateUserInfo")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }
    //更新密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization")String token){
        //1.参数校验
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }

        //原密码是否正确
        //调用userService根据用户名拿到原密码，在和old_Pwd比较
        Map<String, Object>map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User LoginUser = userService.findByUserName(username);
        if (!LoginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }

        //newPwd和rePwd是否一致
        if (!rePwd.equals((newPwd))){
            return Result.error("两次密码不一致");
        }

        //2.调用service完成密码更新
        userService.updatePwd(newPwd);
        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
    //查询宿舍楼列表
    @GetMapping("/dormList")
    public Result list(){
        List<Dorms> dorms = dormService.list();
        return Result.success(dorms);
    }

    @PostMapping("/addStudent")
    public Result add(@RequestBody Students students) {
        // 添加学生
        studentService.add(students);

        // 获取生成的ID
        Integer studentId = students.getId();

        // 添加出勤记录
        attendanceService.add(studentId);

        // 可以选择重新查询完整的学生信息
        Students completeStudentInfo = studentService.getById(studentId);

        // 返回完整的学生信息
        return Result.success(completeStudentInfo);
    }

    @PostMapping("/selectStudentUser")
    public Result selectStudentUser(@RequestParam Integer userId,@RequestParam Integer studentId){
        studentUserService.selectStudentUser(userId,studentId);
        return Result.success();
    }

    @PostMapping("/updateCount")
    public Result updateCount(@RequestParam Integer id) {
        //获取room信息
        DormRooms room = dormRoomService.getRoomById(id);

        if (room == null) {
            return Result.error("房间不存在");
        }

        //判断房间是否已经被已满
        if (room.getCurrentCount() < room.getCapacity()) {
            dormRoomService.updateCount(id);
            return Result.success("更新成功");
        } else {
            return Result.error("房间已满");
        }
    }

    @PostMapping("/checkUserStudent")
    public Result checkUserStudent(@RequestParam Integer userId) {
        Students student = studentUserService.checkUserStudent(userId);
        if (student == null) {
            return Result.error("该用户未绑定学生信息");
        }
        return Result.success(student);
    }

    @GetMapping("/noticeList")
    public Result noticeList() {
        List<Notices> notices = noticesService.list();
        return Result.success(notices);
    }

    @PostMapping("/signIn")
    public Result signIn(@RequestParam Integer studentId) {
        // 获取当前时间
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(20, 30); // 晚上 8:30
        LocalTime end = LocalTime.of(23, 0); // 晚上 11:00

        // 判断签到状态
        String status = (now.isAfter(start) && now.isBefore(end)) ? "正常" : "晚归";

        // 调用签到服务
        attendanceService.signIn(studentId, status);

        return Result.success("签到成功，状态：" + status);
    }



}
