package org.example.pojo;

import lombok.Data;

@Data
public class UserRoles {

    private int userId;
    private int roleId;

    public UserRoles(int userId, int id) {
    }
}
