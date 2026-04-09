package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Students {
    private int id;
    private String studentId;
    private String name;
    private String gender;
    private int group;
    private String phone;
    private String major;
    private int enrollmentYear;
    private int dormId;
    private int roomId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedTime;
}
