package org.example.pojo;

import lombok.Data;

@Data
public class DormRoomQueryParam {
    private String building;
    private String dormNumber;
    private Integer floor;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
