package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.UserEntity;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getOwnUser(String email) throws EntityNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(EntityType.USER, email));
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
