package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.UserEntity;
import com.example.educationalbackend.exception.EntityNotFoundException;
import com.example.educationalbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getOwnUser(String email) throws EntityNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }
}
