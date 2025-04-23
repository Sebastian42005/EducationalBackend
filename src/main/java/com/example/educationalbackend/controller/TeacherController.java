package com.example.educationalbackend.controller;

import com.example.educationalbackend.entity.SubjectEntity;
import com.example.educationalbackend.entity.TeacherEntity;
import com.example.educationalbackend.service.TeacherService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PutMapping("/{id}/subject/{subjectId}")
    public ResponseEntity<SubjectEntity> addCourseToTeacher(@PathVariable int id, @PathVariable int subjectId) {
        return ResponseEntity.ok(teacherService.addSubjectToTeacher(id, subjectId));
    }

    @GetMapping
    public ResponseEntity<List<TeacherEntity>> getTeachers() {
        return ResponseEntity.ok(teacherService.getTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherEntity> getTeacher(@PathVariable int id) {
        return ResponseEntity.ok(teacherService.getTeacher(id));
    }
}
