package com.example.educationalbackend.controller;

import com.example.educationalbackend.entity.SubjectEntity;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public SubjectEntity createSubject(@RequestBody SubjectEntity subjectEntity) {
        return subjectService.createSubject(subjectEntity);
    }

    @PutMapping("/{id}/image")
    public Map<String, String> putImageToSubject(@PathVariable int id, @RequestParam("file") MultipartFile image) throws EntityNotFoundException, IOException {
        return subjectService.putImageToSubject(id, image);
    }

    @PutMapping
    public SubjectEntity updateSubject(@RequestBody SubjectEntity subjectEntity) {
        return subjectService.updateSubject(subjectEntity);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImageFromSubject(@PathVariable int id) throws EntityNotFoundException {
        return subjectService.getImageFromSubject(id);
    }

    @GetMapping
    public List<SubjectEntity> getAllSubjects(Principal principal) {
        return subjectService.getAllSubjects(principal);
    }

    @GetMapping("/no-auth")
    public List<SubjectEntity> getAllSubjectsNoAuth() {
        return subjectService.getAllSubjectsNoAuth();
    }

    @GetMapping("/not-free")
    public List<SubjectEntity>getAllNotFreeSubjects() {
        return subjectService.getAllNotFreeSubjects();
    }

    @GetMapping("/{id}")
    public SubjectEntity getSubjectById(@PathVariable int id) throws EntityNotFoundException {
        return subjectService.getSubject(id);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteSubject(@PathVariable int id) {
        subjectService. deleteSubject(id);
        return Map.of("message", "Subject deleted successfully");
    }
}
