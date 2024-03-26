package com.example.educationalbackend.dto.mapper;

import com.example.educationalbackend.dto.UserDto;
import com.example.educationalbackend.entity.UserEntity;

import java.util.function.Function;

public class UserMapper implements Function<UserEntity, UserDto> {
    @Override
    public UserDto apply(UserEntity userEntity) {
        return new UserDto(userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getRole(),
                userEntity.getStudent(),
                userEntity.getTeacher());
    }
}
