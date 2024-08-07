package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.StudentEntity;
import com.example.educationalbackend.entity.SubjectEntity;
import com.example.educationalbackend.entity.TeacherEntity;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.repository.StudentRepository;
import com.example.educationalbackend.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {

    private final SubjectService subjectService;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public TeacherService(SubjectService subjectService, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.subjectService = subjectService;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public TeacherEntity createTeacher(TeacherEntity teacherEntity) {
        return teacherRepository.save(teacherEntity);
    }

    @Transactional(readOnly = true)
    public TeacherEntity getTeacher(int id) {
        return teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.TEACHER, id));
    }

    @Transactional(readOnly = true)
    public List<TeacherEntity> getTeachers() {
        return teacherRepository.findAll();
    }

    @Transactional
    public SubjectEntity addSubjectToTeacher(int id, int subjectId) {
        SubjectEntity subject = subjectService.getSubject(subjectId);
        TeacherEntity teacherEntity = teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.TEACHER, id));
        teacherEntity.getSubjects().add(subject);
        subject.getTeachers().add(teacherEntity);
        return subject;
    }

    @Transactional
    public TeacherEntity addStudentToTeacher(int id, int studentId) {
        TeacherEntity teacherEntity = teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.TEACHER, id));
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.STUDENT, id));
        teacherEntity.getStudents().add(studentEntity);
        studentEntity.getTeachers().add(teacherEntity);
        return teacherEntity;
    }
}
