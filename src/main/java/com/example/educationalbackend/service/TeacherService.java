package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.SubjectEntity;
import com.example.educationalbackend.entity.TeacherEntity;
import com.example.educationalbackend.exception.EntityNotFoundException;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final SubjectService subjectService;
    private final TeacherRepository teacherRepository;

    public TeacherService(SubjectService subjectService, TeacherRepository teacherRepository) {
        this.subjectService = subjectService;
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public SubjectEntity addSubjectToTeacher(int id, int subjectId) {
        SubjectEntity subject = subjectService.getSubject(subjectId);
        TeacherEntity teacherEntity = getTeacher(id);
        teacherEntity.getSubjects().add(subject);
        subject.getTeachers().add(teacherEntity);
        return subject;
    }

    public TeacherEntity getTeacher(int id) {
        return teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.TEACHER));
    }

    public List<TeacherEntity> getTeachers() {
        return teacherRepository.findAll();
    }
}
