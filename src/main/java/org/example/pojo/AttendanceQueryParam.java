package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceQueryParam {
    private Integer studentId;
    private String name;
    private Integer group;
    private String status;
    private Integer dormNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Integer pageNum = 1;   // 当前页码，默认第1页
    private Integer pageSize = 10; // 每页大小，默认10条
}
