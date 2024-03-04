package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.SubjectEntity;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.repository.SubjectRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public SubjectEntity getSubject(int id) {
        return subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.SUBJECT, id));
    }

    public SubjectEntity getSubjectEntity(SubjectEntity subjectEntity) {
        subjectEntity.setId(0);
        return subjectRepository.save(subjectEntity);
    }

    public Map<String, String> putImageToSubject(int id, MultipartFile image) throws IOException {
        SubjectEntity subjectEntity = subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.SUBJECT, id));
        subjectEntity.setImage(image.getBytes());
        subjectEntity.setImageType(image.getContentType());
        subjectRepository.save(subjectEntity);
        return Map.of("message", "Image added successfully");
    }

    public ResponseEntity<byte[]> getImageFromSubject(int id) {
        SubjectEntity subjectEntity = subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.SUBJECT, id));
        return ResponseEntity.ok().contentType(MediaType.valueOf(subjectEntity.getImageType())).body(subjectEntity.getImage());
    }

    public List<SubjectEntity> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public void deleteSubject(int id) {
        subjectRepository.deleteById(id);
    }
}
