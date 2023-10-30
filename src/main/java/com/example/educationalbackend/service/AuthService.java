package com.example.educationalbackend.service;

import com.example.educationalbackend.config.JwtTokenUtil;
import com.example.educationalbackend.config.JwtUserDetailsService;
import com.example.educationalbackend.config.Role;
import com.example.educationalbackend.config.ShaUtils;
import com.example.educationalbackend.dto.AuthenticationRequest;
import com.example.educationalbackend.entity.UserEntity;
import com.example.educationalbackend.exception.WrongLoginCredentialsException;
import com.example.educationalbackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil tokenUtil;
    private final UserRepository userRepository;

    public AuthService(JwtUserDetailsService userDetailsService, JwtTokenUtil tokenUtil, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.tokenUtil = tokenUtil;
        this.userRepository = userRepository;
    }

    public Map<String, String> login(AuthenticationRequest authenticationRequest) throws WrongLoginCredentialsException {
        final UserDetails userDetails = userDetailsService.verifyUser(authenticationRequest.getEmail(), ShaUtils.decode(authenticationRequest.getPassword()));
        final String token = tokenUtil.generateToken(userDetails);
        return Map.of("token", token);
    }

    public UserEntity register(UserEntity userEntity) {
        Optional<UserEntity> user = userRepository.findByEmail(userEntity.getEmail());
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        userEntity.setId(null);
        userEntity.setPassword(ShaUtils.decode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return userEntity;
    }

}
