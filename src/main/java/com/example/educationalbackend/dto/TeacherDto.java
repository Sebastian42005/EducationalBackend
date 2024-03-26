package com.example.educationalbackend.dto;


import java.util.List;

public record TeacherDto(int id, List<Integer> students, List<SubjectDto> subjects) {
}
