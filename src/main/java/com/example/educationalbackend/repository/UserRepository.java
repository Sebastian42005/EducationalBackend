package com.example.educationalbackend.repository;

import com.example.educationalbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.password = :password")
    Optional<UserEntity> login(String email, String password);

    Optional<UserEntity> findByEmail(String email);
}
