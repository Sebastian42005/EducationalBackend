package com.example.educationalbackend.repository;

import com.example.educationalbackend.entity.TeacherEntity;
import com.example.educationalbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {

}
