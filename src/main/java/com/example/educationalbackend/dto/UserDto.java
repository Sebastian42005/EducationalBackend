package com.example.educationalbackend.dto;

import com.example.educationalbackend.entity.LessonEntity;
import com.example.educationalbackend.entity.StudentEntity;
import com.example.educationalbackend.entity.TeacherEntity;
import com.example.educationalbackend.entity.enums.UserRole;

public record UserDto (int id,
                       String email,
                       String firstName,
                       String lastName,
                       UserRole role,
                       StudentEntity student,
                       TeacherEntity teacher) {
}
