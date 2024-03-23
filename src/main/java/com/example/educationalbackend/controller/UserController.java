package com.example.educationalbackend.controller;

import com.example.educationalbackend.entity.UserEntity;
import com.example.educationalbackend.exception.exceptions.UserNotLoggedInException;
import com.example.educationalbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/own")
    UserEntity getOwnUser(Principal principal) {
        if (principal == null) throw new UserNotLoggedInException();
        return userService.getOwnUser(principal.getName());
    }

    @GetMapping
    List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    Map<String, String> deleteUser(@PathVariable int id) {
        this.userService.deleteUser(id);
        return Map.of("message", "Deleted user successfully");
    }
}
