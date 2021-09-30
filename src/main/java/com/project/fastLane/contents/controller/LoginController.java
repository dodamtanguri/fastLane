package com.project.fastLane.contents.controller;

import com.project.fastLane.commons.exception.CustomRequestException;
import com.project.fastLane.config.filter.JwtTokenProvider;
import com.project.fastLane.contents.model.request.LoginReq;
import com.project.fastLane.contents.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "FASTLANE 회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class LoginController {
    public final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "로그인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomRequestException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CustomRequestException.class)
    })
    @PostMapping("/login")
    public String login(@RequestBody LoginReq req) throws IllegalAccessException {
        userService.loginUser(req);
        return jwtTokenProvider.generateToken(req.getEmail());
    }
}
