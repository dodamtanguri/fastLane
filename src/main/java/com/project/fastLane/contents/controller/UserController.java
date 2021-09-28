package com.project.fastLane.contents.controller;

import com.project.fastLane.contents.model.UserReq;
import com.project.fastLane.contents.model.dto.UserDto;
import com.project.fastLane.contents.model.response.UserRes;
import com.project.fastLane.contents.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Environment env;

    /**
     *
     * @param req
     * @return
     */
    @PostMapping("/users")
    public ResponseEntity<UserRes> createUser(@RequestBody UserReq req) {
        UserDto userDto = new ModelMapper().map(req, UserDto.class);
        userDto = userService.createUser(userDto);

        UserRes res = new ModelMapper().map(userDto, UserRes.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }
}
