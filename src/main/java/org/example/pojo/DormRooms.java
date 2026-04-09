package org.example.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DormRooms {
    private Integer id;
    private String dormNumber;
    private int floor;
    private int currentCount;
    private int capacity;
    private int dormId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedTime;
}
