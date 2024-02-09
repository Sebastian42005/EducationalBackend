package com.example.educationalbackend.controller;

import com.example.educationalbackend.entity.UserEntity;
import com.example.educationalbackend.exception.EntityNotFoundException;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/own")
    UserEntity getOwnUser(Principal principal) throws EntityNotFoundException {
        if (principal == null) throw new EntityNotFoundException(EntityType.USER);
        return userService.getOwnUser(principal.getName());
    }
}
