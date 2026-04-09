package org.example.service;

import org.example.pojo.Students;
import org.example.pojo.User;

import java.util.List;

public interface StudentUserService {
    Students getStudentInfoById(Integer userId);


    void selectStudentUser(Integer userId, Integer studentId);

    Students checkUserStudent(Integer userId);

    List<User> listUnbound();

    List<User> checkStudentUser(Integer studentId);

    void updateStudentUser(Integer userId, Integer studentId);

    Integer checkUserBound(Integer userId);

    void unbindStudentUser(Integer studentId);
}
