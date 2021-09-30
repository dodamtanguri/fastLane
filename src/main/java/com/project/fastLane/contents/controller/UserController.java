package com.project.fastLane.contents.controller;

import com.project.fastLane.commons.exception.CustomRequestException;
import com.project.fastLane.config.filter.JwtTokenProvider;
import com.project.fastLane.contents.model.dto.UserDto;
import com.project.fastLane.contents.model.request.LoginReq;
import com.project.fastLane.contents.model.request.PasswordReq;
import com.project.fastLane.contents.model.request.UserReq;
import com.project.fastLane.contents.model.response.PasswordRes;
import com.project.fastLane.contents.model.response.UserRes;
import com.project.fastLane.contents.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Api(tags = "FASTLANE 회원 관리 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Environment env;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     *
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


    @ApiOperation(value = "회원삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomRequestException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CustomRequestException.class)
    })
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestParam(name = "email") String email) throws Exception {
        try {
            userService.deleteUser();
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "회원 비밀번호 변경")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomRequestException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CustomRequestException.class)
    })
    @PutMapping(value = "/password")
    public ResponseEntity<PasswordRes> modifyPassword(@Valid @RequestBody PasswordReq req) {
        return null;
    }
}
