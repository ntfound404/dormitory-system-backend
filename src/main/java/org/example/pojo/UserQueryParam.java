package org.example.pojo;

import lombok.Data;

@Data
public class UserQueryParam {
    private String username;
    private String nickname;
    private String email;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
