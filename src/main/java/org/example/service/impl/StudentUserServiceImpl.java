package org.example.service.impl;

import org.example.mapper.StudentUserMapper;
import org.example.pojo.Students;
import org.example.pojo.User;
import org.example.service.StudentService;
import org.example.service.StudentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentUserServiceImpl implements StudentUserService {
    @Override
    public void unbindStudentUser(Integer studentId) {
        studentUserMapper.unbindStudentUser(studentId);
    }

    @Override
    /**
     * 查询用户是否已经绑定学生
     * @param userId 用户ID
     * @return 绑定的学生ID（如果没有绑定，返回 null）
     */
    public Integer checkUserBound(Integer userId) {
        return studentUserMapper.findStudentIdByUserId(userId);
    }

    @Override
    public void updateStudentUser(Integer userId, Integer studentId) {
        studentUserMapper.updateStudentUser(userId, studentId);
    }

    @Override
    public List<User> checkStudentUser(Integer studentId) {
        return studentUserMapper.checkStudentUser(studentId);
    }

    @Override
    public List<User> listUnbound() {
        return studentUserMapper.listUnbound();
    }

    @Override
    public Students checkUserStudent(Integer userId) {
        return studentUserMapper.checkUserStudent(userId);
    }

    @Override
    public void selectStudentUser(Integer userId, Integer studentId) {
        studentUserMapper.selectStudentUser(userId, studentId);
    }

    @Override
    public Students getStudentInfoById(Integer userId) {
        return studentUserMapper.getStudentInfoById(userId);
    }

    @Autowired
    private StudentUserMapper studentUserMapper;
}
