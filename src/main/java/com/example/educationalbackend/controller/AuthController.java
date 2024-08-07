package com.example.educationalbackend.controller;

import com.example.educationalbackend.dto.AuthenticationRequest;
import com.example.educationalbackend.dto.AuthenticationResponse;
import com.example.educationalbackend.dto.RegisterRequest;
import com.example.educationalbackend.entity.UserEntity;
import com.example.educationalbackend.exception.exceptions.WrongLoginCredentialsException;
import com.example.educationalbackend.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) throws WrongLoginCredentialsException {
        if (authenticationRequest.password() == null || authenticationRequest.email() == null) {
            throw new WrongLoginCredentialsException();
        }
        return authService.login(authenticationRequest);
    }

    @PostMapping("/register")
    public UserEntity register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest.getUserEntity());
    }
}
