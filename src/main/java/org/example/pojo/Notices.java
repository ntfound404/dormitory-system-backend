package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Notices {
    private int id;
    private String title;
    private String content;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp createdTime;
}
