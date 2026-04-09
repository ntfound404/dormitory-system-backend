package org.example.controller;

import org.example.pojo.Dorms;
import org.example.pojo.DormsQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.DormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dorm")
public class DormController {
    @Autowired
    private DormService dormService;

    @PostMapping("/add")
    public Result add(@RequestBody Dorms dorms){
        dormService.add(dorms);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Dorms dorms){
        dormService.update(dorms);
        return Result.success();
    }

    @PostMapping
    public Result delete(@RequestParam Integer id){
        dormService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Dorms> dorms = dormService.list();
        return Result.success(dorms);
    }

    // 批量删除宿舍
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        dormService.deleteBatch(ids);
        return Result.success();
    }

    @PostMapping("/query")
    public Result<PageResult<Dorms>> query(@RequestBody DormsQueryParam param){
        PageResult<Dorms> pageResult = dormService.queryDorms(param);
        return Result.success(pageResult);
    }
}
