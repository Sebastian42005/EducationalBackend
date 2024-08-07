package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.*;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.exception.exceptions.UserNotLoggedInException;
import com.example.educationalbackend.repository.FileRepository;
import com.example.educationalbackend.repository.SubjectRepository;
import com.example.educationalbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public SubjectEntity getSubject(int id) {
        return subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.SUBJECT, id));
    }

    public SubjectEntity createSubject(SubjectEntity subjectEntity) {
        subjectEntity.setId(0);
        return subjectRepository.save(subjectEntity);
    }

    public SubjectEntity updateSubject(SubjectEntity subjectEntity) {
        SubjectEntity dbSubject = subjectRepository.findById(subjectEntity.getId()).orElseThrow(() -> new EntityNotFoundException(EntityType.SUBJECT, subjectEntity.getId()));
        dbSubject.setName(subjectEntity.getName());
        dbSubject.setFree(subjectEntity.isFree());
        dbSubject.setDescription(subjectEntity.getDescription());
        return subjectRepository.save(subjectEntity);
    }

    public Map<String, String> putImageToSubject(int id, MultipartFile image) throws IOException {
        SubjectEntity subjectEntity = subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.SUBJECT, id));
        FileEntity file = new FileEntity(image.getBytes(), image.getContentType());
        fileRepository.save(file);
        subjectEntity.setFile(file.getId());
        subjectRepository.save(subjectEntity);
        return Map.of("message", "Image added successfully");
    }

    public ResponseEntity<byte[]> getImageFromSubject(int id) {
        SubjectEntity subjectEntity = subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.SUBJECT, id));
        FileEntity file = fileRepository.findById(subjectEntity.getFile()).orElseThrow(() -> new EntityNotFoundException(EntityType.FILE, id));
        return ResponseEntity.ok().contentType(MediaType.valueOf(file.getFileType())).body(file.getContent());
    }

    public List<SubjectEntity> getAllSubjectsNoAuth() {
        return subjectRepository.findAll().stream().map(subject -> SubjectEntity.builder()
                .id(subject.getId())
                .name(subject.getName())
                .description(subject.getDescription()).build()).toList();
    }

    public List<SubjectEntity> getAllSubjects(Principal principal) {
        if (principal == null) throw new UserNotLoggedInException();
        UserEntity user = userRepository.findByEmail(principal.getName()).orElseThrow(UserNotLoggedInException::new);
        return switch (user.getRole()) {
            case ADMIN -> subjectRepository.findAll();
            case TEACHER -> getTeacherSubjects(user.getTeacher());
            case STUDENT -> getStudentSubjects(user.getStudent());
        };
    }

    private List<SubjectEntity> getTeacherSubjects(TeacherEntity teacher) {
        List<SubjectEntity> subjects = subjectRepository.getAllFree();
        subjects.addAll(subjectRepository.getTeacherSubjects(teacher.getId()));
        return subjects;
    }

    private List<SubjectEntity> getStudentSubjects(StudentEntity student) {
        List<SubjectEntity> subjects = subjectRepository.getAllFree();
        student.getTeachers().forEach(teacher -> subjects.addAll(getTeacherSubjects(teacher)));
        return subjects;
    }

    public void deleteSubject(int id) {
        subjectRepository.deleteById(id);
    }

    public List<SubjectEntity> getAllNotFreeSubjects() {
        return subjectRepository.getAllNotFree();
    }
}
