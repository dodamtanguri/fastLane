package com.project.fastLane.contents.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
//boilerplate 생성
@JsonInclude(Include.NON_NULL)
public class UserRes {
    private String email;
    private String name;
}
