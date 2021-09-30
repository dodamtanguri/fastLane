package com.project.fastLane.contents.controller;

import com.project.fastLane.config.filter.JwtTokenProvider;
import com.project.fastLane.contents.model.dto.UserDto;
import com.project.fastLane.contents.model.request.LoginReq;
import com.project.fastLane.contents.model.request.PasswordReq;
import com.project.fastLane.contents.model.request.UserReq;
import com.project.fastLane.contents.model.response.UserRes;
import com.project.fastLane.contents.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "FASTLANE 회원 관리 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper mapper;

    /**
     * 회원가입
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "회원가입")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/users")
    public ResponseEntity<UserRes> createUser(@Valid @RequestBody UserReq req) {
        UserDto userDto = mapper.map(req, UserDto.class);
        userDto = userService.createUser(userDto);

        UserRes res = new ModelMapper().map(userDto, UserRes.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }

    /**
     * 로그인
     *
     * @param req
     * @return JWT token
     * @throws IllegalAccessException
     */
    @ApiOperation(value = "로그인")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/login")
    public String login(@RequestBody LoginReq req) throws IllegalAccessException {
        userService.loginUser(req);
        return jwtTokenProvider.generateToken(req.getEmail());
    }

    /**
     * 회원 삭제
     *
     * @param email
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "회원삭제")
    @ApiResponses({
        @ApiResponse(code = 204, message = "OK"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestParam(name = "email") String email) {
        try {
            userService.deleteUser(email);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "회원 비밀번호 변경")
    @ApiResponses({
        @ApiResponse(code = 204, message = "OK"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/password")
    public ResponseEntity<?> modifyPassword(@Valid @RequestBody PasswordReq req) {
        try {
            userService.modifyPassword(req);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
