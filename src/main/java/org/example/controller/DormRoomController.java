package org.example.controller;


import org.example.pojo.*;
import org.example.service.DormRoomService;
import org.example.service.DormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/room")
public class DormRoomController {

    @Autowired
    private DormRoomService dormRoomService;

    @Autowired
    private DormService dormService;

    @GetMapping("/list")
    public Result list() {
        List<DormRooms> rooms = dormRoomService.list();
        return Result.success(rooms);
    }

    @PostMapping("/add")
    public Result add(@RequestBody DormRooms rooms) {
        dormRoomService.add(rooms);
        return Result.success();
    }

    //通过dormId查询宿舍楼
    @GetMapping("/dorm")
    public Result listByDormId(@RequestParam Integer dormId) {
        List<Dorms> dorms = dormService.listByDormId(dormId);
        return Result.success(dorms);
    }


    @PostMapping
    public Result delete(@RequestParam Integer id) {
        dormRoomService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody DormRooms rooms) {
        dormRoomService.update(rooms);
        return Result.success();
    }

    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        dormRoomService.deleteBatch(ids);
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

    @PostMapping("/deleteCount")
    public Result deleteCount(@RequestParam Integer id) {
        dormRoomService.deleteCount(id);
        return Result.success();
    }

    @GetMapping("/studentList")
    public Result getStudentList(@RequestParam Integer id) {
        List<Students> studentList = dormRoomService.getStudentList(id);
        return Result.success(studentList);
    }

    @PostMapping("/query")
    public Result<PageResult<DormRooms>> query(@RequestBody DormRoomQueryParam param) {
        PageResult<DormRooms> pageResult = dormRoomService.queryDormRoom(param);
        System.out.println(param);
        return Result.success(pageResult);
    }


}


