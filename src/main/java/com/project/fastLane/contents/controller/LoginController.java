package com.project.fastLane.contents.controller;

import com.project.fastLane.commons.exception.CustomRequestException;
import com.project.fastLane.contents.model.request.UserReq;
import com.project.fastLane.contents.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "FASTLANE 회원 API")
@RestController
@RequiredArgsConstructor
public class LoginController {
    public final UserService userService;

    @ApiOperation(value = "로그인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomRequestException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CustomRequestException.class)
    })
    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserReq req) {
        return null;
    }
}
