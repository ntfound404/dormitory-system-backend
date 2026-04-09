package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long total;       // 总记录数
    private Integer pages;    // 总页数
    private List<T> records;  // 当前页数据列表
}