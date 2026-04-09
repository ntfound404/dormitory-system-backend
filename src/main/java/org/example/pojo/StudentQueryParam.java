package org.example.pojo;

import lombok.Data;

@Data
public class StudentQueryParam {
    private String studentId;      // 学号
    private String name;           // 学生姓名
    private String gender;         // 性别
    private Integer group;         // 班级组
    private String phone;          // 手机号
    private String major;          // 专业
    private Integer enrollmentYear; // 入学年份
    private String building;        // 宿舍楼ID
    private Integer dormNumber;        // 寝室号
    private Integer pageNum = 1;   // 当前页码，默认第1页
    private Integer pageSize = 10; // 每页大小，默认10条
}