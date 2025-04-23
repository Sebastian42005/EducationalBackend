package com.example.educationalbackend.repository;

import com.example.educationalbackend.entity.ClassEntity;
import com.example.educationalbackend.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<ClassEntity, Integer> {

    Optional<ClassEntity> findByRoomCode(Integer roomCode);

    Boolean existsByRoomCode(Integer roomCode);
}
