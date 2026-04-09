package org.example.service;

import org.example.pojo.PageResult;
import org.example.pojo.StudentQueryParam;
import org.example.pojo.Students;

import java.util.List;

public interface StudentService {
    void add(Students students);


    void update(Students students);

    void deleteById(Integer id);

    List<Students> list();

    void deleteBatch(List<Integer> ids);

    Students getById(Integer studentId);

    PageResult<Students> queryStudents(StudentQueryParam param);
}
