package com.project.fastLane.contents.controller;

import com.project.fastLane.commons.exception.CustomRequestException;
import com.project.fastLane.contents.model.UserReq;
import com.project.fastLane.contents.model.dto.UserDto;
import com.project.fastLane.contents.model.response.UserRes;
import com.project.fastLane.contents.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "FASTLANE 회원 관리 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Environment env;

    /**
     * @param req
     * @return
     */
    @ApiOperation(value = "회원가입")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomRequestException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CustomRequestException.class)
    })
    @PostMapping("/users")
    public ResponseEntity<UserRes> createUser(@Valid @RequestBody UserReq req) {
        UserDto userDto = new ModelMapper().map(req, UserDto.class);
        userDto = userService.createUser(userDto);

        UserRes res = new ModelMapper().map(userDto, UserRes.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }
    

}
