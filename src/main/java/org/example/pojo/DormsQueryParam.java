package org.example.pojo;

import lombok.Data;

@Data
public class DormsQueryParam {
    private String building;
    private String gender;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
