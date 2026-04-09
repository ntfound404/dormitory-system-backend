package org.example.controller;

import org.example.pojo.Notices;
import org.example.pojo.NoticesQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.NoticesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/notices")
public class NoticesController {

    @Autowired
    private NoticesService noticesService;

    // 获取公告列表，不分页
    @GetMapping("/list")
    public Result list() {
        List<Notices> notices = noticesService.list();
        return Result.success(notices);
    }


    // 添加公告
    @PostMapping("/add")
    public Result add(@RequestBody Notices notices) {
        noticesService.add(notices);
        return Result.success();
    }

    // 删除单个公告
    @PostMapping
    public Result delete(@RequestParam Integer id) {
        noticesService.deleteById(id);
        return Result.success();
    }

    // 批量删除公告
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        noticesService.deleteBatch(ids);
        return Result.success();
    }

    // 更新公告
    @PostMapping("/update")
    public Result update(@RequestBody Notices notices) {
        noticesService.update(notices);
        return Result.success();
    }

    @PostMapping("/query")
    public Result<PageResult<Notices>> query(@RequestBody NoticesQueryParam param) {
        PageResult<Notices> pageResult = noticesService.queryNotices(param);
        System.out.println(param);
        return Result.success(pageResult);
    }

}
