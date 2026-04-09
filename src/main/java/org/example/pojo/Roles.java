package org.example.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Roles {
    private int id;
    private String roleName;
    private String description;
}
