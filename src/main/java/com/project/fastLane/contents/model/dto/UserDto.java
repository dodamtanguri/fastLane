package com.project.fastLane.contents.model.dto;

import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {

    private String email;
    private String password;
    private String name;
    private Date createDate;
}
