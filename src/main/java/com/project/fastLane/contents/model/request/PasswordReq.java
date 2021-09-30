package com.project.fastLane.contents.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordReq {

    @NotNull(message = "New Password cannot be null")
    @Size(min = 8, message = "Password must be equal or grater than 8 characters")
    private String newPassword;
}
