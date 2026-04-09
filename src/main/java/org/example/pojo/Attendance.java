package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Attendance {
    private int id;
    private int studentId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;
    private String status;
    private String remarks;
}
