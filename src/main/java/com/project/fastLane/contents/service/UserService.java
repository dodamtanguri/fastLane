package com.project.fastLane.contents.service;

import com.project.fastLane.contents.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserID(String UserId);

    UserDto getUserDetailsByEmail(String email);
}
