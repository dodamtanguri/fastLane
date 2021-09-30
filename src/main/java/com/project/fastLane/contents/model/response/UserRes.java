package com.project.fastLane.contents.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRes {

    private String email;
    private String name;
}
