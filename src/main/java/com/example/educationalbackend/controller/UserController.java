package com.example.educationalbackend.controller;

import com.example.educationalbackend.dto.UserDto;
import com.example.educationalbackend.dto.mapper.UserMapper;
import com.example.educationalbackend.entity.UserEntity;
import com.example.educationalbackend.exception.exceptions.UserNotLoggedInException;
import com.example.educationalbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper = new UserMapper();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/own")
    UserDto getOwnUser(Principal principal) {
        if (principal == null) throw new UserNotLoggedInException();
        return userMapper.apply(userService.getOwnUser(principal.getName()));
    }

    @GetMapping
    List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream().map(userMapper).toList();
    }

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable int id) {
        return userMapper.apply(userService.getUser(id));
    }

    @DeleteMapping("/{id}")
    Map<String, String> deleteUser(@PathVariable int id) {
        this.userService.deleteUser(id);
        return Map.of("message", "Deleted user successfully");
    }

    @PutMapping
    UserDto updateUser(@RequestBody UserEntity user) {
        return this.userService.updateUser(user);
    }

    @GetMapping("/workshop-requests")
    public ResponseEntity<List<UserDto>> getUsersWithWorkshopRequests() {
        return ResponseEntity.ok(userService.getUsersWithWorkshopRequests());
    }
}
