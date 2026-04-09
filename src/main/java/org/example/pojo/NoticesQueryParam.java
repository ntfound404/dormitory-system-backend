package org.example.pojo;

import lombok.Data;

@Data
public class NoticesQueryParam {
    private String title;
    private String author;
    private Integer pageNum = 1;   // 当前页码，默认第1页
    private Integer pageSize = 10; // 每页大小，默认10条
}
