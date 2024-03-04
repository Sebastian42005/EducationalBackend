package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.StudentEntity;
import com.example.educationalbackend.entity.SubjectEntity;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional
    public StudentEntity createStudent(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }

    @Transactional(readOnly = true)
    public StudentEntity getStudent(int id) {
        return studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.STUDENT, id));
    }

    @Transactional(readOnly = true)
    public List<SubjectEntity> getSubjects(int id) {
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.STUDENT, id));
        List<SubjectEntity> subjectEntities = new ArrayList<>();
        studentEntity.getTeachers().forEach(teacher -> subjectEntities.addAll(teacher.getSubjects()));
        return subjectEntities;
    }
}
