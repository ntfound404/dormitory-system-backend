package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.StudentMapper;
import org.example.pojo.PageResult;
import org.example.pojo.StudentQueryParam;
import org.example.pojo.Students;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Override
    public PageResult<Students> queryStudents(StudentQueryParam param) {
        // 开启分页
        PageHelper.startPage(param.getPageNum(), param.getPageSize());

        // 调用Mapper进行动态查询
        Page<Students> page = (Page<Students>) studentMapper.queryByCondition(param);

        // 返回分页结果
        return new PageResult<>(
                page.getTotal(),    // 总记录数
                page.getPages(),    // 总页数
                page.getResult()    // 当前页数据
        );
    }

    @Override
    public Students getById(Integer studentId) {
        return studentMapper.getById(studentId);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        studentMapper.deleteBatch(ids);
    }

    @Override
    public void update(Students students) {
        studentMapper.update(students);
    }

    @Override
    public List<Students> list() {
        return studentMapper.list();
    }

    @Override
    public void deleteById(Integer id) {
        studentMapper.deleteById(id);
    }

    @Override
    public void add(Students students) {
        studentMapper.add(students);
    }

    @Autowired
    private StudentMapper studentMapper;
}
