package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;


@Data

public class User {
    @NotNull
    private int id;
    private String username;
    @JsonIgnore
    private String password;
    private int role;
    private String nickname;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp updatedTime;
}
