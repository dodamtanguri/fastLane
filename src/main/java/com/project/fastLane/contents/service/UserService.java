package com.project.fastLane.contents.service;

import com.project.fastLane.contents.model.dto.UserDto;
import com.project.fastLane.contents.model.request.LoginReq;
import com.project.fastLane.contents.model.request.PasswordReq;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    void loginUser(LoginReq userInfo) throws IllegalAccessException;

    void deleteUser(String email);

    void modifyPassword(PasswordReq req);
}
