package com.akul.taskslist.service.impl;

import com.akul.taskslist.domain.user.User;
import com.akul.taskslist.service.AuthService;
import com.akul.taskslist.service.UserService;
import com.akul.taskslist.web.dto.auth.JwtRequest;
import com.akul.taskslist.web.dto.auth.JwtResponse;
import com.akul.taskslist.web.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger LG = LoggerFactory
            .getLogger(AuthServiceImpl.class);

    @Override
    public JwtResponse login(final JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword())
        );
        LG.info("WORK authenticationManager!!!");

        User user = userService.getByUsername(loginRequest.getUsername());
        LG.info("WORK userService!!!", user.getUsername());
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                user.getId(), user.getUsername(), user.getRoles())
        );
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                user.getId(), user.getUsername())
        );
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(final String refreshToken) {

        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

}
