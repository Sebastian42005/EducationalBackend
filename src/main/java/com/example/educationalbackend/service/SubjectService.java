package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.SubjectEntity;
import com.example.educationalbackend.exception.EntityNotFoundException;
import com.example.educationalbackend.repository.SubjectRepository;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public SubjectEntity getSubject(int id) {
        return subjectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
