package com.akul.taskslist.web.controller;


import com.akul.taskslist.domain.user.User;
import com.akul.taskslist.service.AuthService;
import com.akul.taskslist.service.UserService;
import com.akul.taskslist.web.dto.auth.JwtRequest;
import com.akul.taskslist.web.dto.auth.JwtResponse;
import com.akul.taskslist.web.dto.user.UserDto;
import com.akul.taskslist.web.mappers.UserMapper;
import com.akul.taskslist.web.validation.OnCreate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse loging(final @Validated
                              @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(final @Validated(OnCreate.class)
                                  @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createUser = userService.create(user);
        return userMapper.toDto(createUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(final @RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }

}
