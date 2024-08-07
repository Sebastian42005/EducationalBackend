package com.example.educationalbackend.repository;

import com.example.educationalbackend.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonEntity, Integer> {
}
