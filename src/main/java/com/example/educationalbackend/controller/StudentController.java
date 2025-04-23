package com.example.educationalbackend.controller;

import com.example.educationalbackend.entity.StudentEntity;
import com.example.educationalbackend.entity.SubjectEntity;
import com.example.educationalbackend.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentEntity>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentEntity> getStudent(@PathVariable int id){
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PostMapping
    public ResponseEntity<StudentEntity> createStudent(StudentEntity studentEntity){
        return ResponseEntity.ok(studentService.createStudent(studentEntity));
    }
}
