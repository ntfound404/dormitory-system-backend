package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Dorms {
    private int id;
    private String building;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedTime;
}
