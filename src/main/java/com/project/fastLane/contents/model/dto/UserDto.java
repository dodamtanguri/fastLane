package com.project.fastLane.contents.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String userId;
    private Date createDate;
    private String encryptedPassword;
}
