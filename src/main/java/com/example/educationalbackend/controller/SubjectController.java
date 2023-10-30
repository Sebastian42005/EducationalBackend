package com.example.educationalbackend.controller;

import com.example.educationalbackend.entity.SubjectEntity;
import com.example.educationalbackend.exception.EntityNotFoundException;
import com.example.educationalbackend.repository.SubjectRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @PostMapping
    public SubjectEntity createSubject(@RequestBody SubjectEntity subjectEntity) {
        subjectEntity.setId(null);
        return subjectRepository.save(subjectEntity);
    }

    @PutMapping("/{id}/image")
    public Map<String, String> putImageToSubject(@PathVariable UUID id, @RequestParam("file") MultipartFile image) throws EntityNotFoundException, IOException {
        SubjectEntity subjectEntity = subjectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        subjectEntity.setImage(image.getBytes());
        subjectEntity.setImageType(image.getContentType());
        subjectRepository.save(subjectEntity);
        return Map.of("message", "Image added successfully");
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImageFromSubject(@PathVariable UUID id) throws EntityNotFoundException {
        SubjectEntity subjectEntity = subjectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.ok().contentType(MediaType.valueOf(subjectEntity.getImageType())).body(subjectEntity.getImage());
    }

    @GetMapping
    public List<SubjectEntity> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @GetMapping("/{id}")
    public SubjectEntity getSubjectById(@PathVariable UUID id) throws EntityNotFoundException {
        return subjectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteSubject(@PathVariable UUID id) {
        subjectRepository.deleteById(id);
        return Map.of("message", "Subject deleted successfully");
    }
}
