package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.*;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final UserService userService;
    private final ClassRepository classRepository;

    public ClassEntity createClass(String name, String description, Principal principal) {
        UserEntity user = userService.getOwnUser(principal.getName());
        ClassEntity classEntity = new ClassEntity();
        classEntity.setName(name);
        classEntity.setDescription(description);
        classEntity.setTeacher(user.getTeacher());
        return classRepository.save(classEntity);
    }

    @Transactional
    public Optional<ClassEntity> joinClass(int roomId, Principal principal) {
        Optional<ClassEntity> optionalClassEntity = classRepository.findByRoomCode(roomId);
        optionalClassEntity.ifPresent(classEntity -> {
            if (classEntity.getExpiresAt().isBefore(LocalDateTime.now())) throw new EntityNotFoundException(EntityType.CLASS, roomId);
            UserEntity user = userService.getOwnUser(principal.getName());
            classEntity.getStudents().add(user.getStudent());
        });

        return optionalClassEntity;
    }

    @Transactional
    public Integer createRoomCode(int classId) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> new EntityNotFoundException(EntityType.CLASS, classId));
        Integer code;
        do {
            code = generateRandomCode();
        } while (Boolean.TRUE.equals(classRepository.existsByRoomCode(code)));
        classEntity.setRoomCode(code);
        classEntity.setExpiresAt(LocalDateTime.now().plusHours(10));
        return code;
    }

    private Integer generateRandomCode() {
        Random random = new SecureRandom();
        return random.nextInt(900_000) + 100_000;
    }

    public List<SubjectEntity> getStudentSubjects(Principal principal) {
        UserEntity user = userService.getOwnUser(principal.getName());
        return user.getStudent().getClasses().stream()
                .flatMap(classEntity -> getSubjects(classEntity.getId()).stream())
                .toList();
    }

    private List<SubjectEntity> getSubjects(int classId) {
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.CLASS, classId));

        return classEntity.getLessons().stream()
                .filter(ClassLessonEntity::isShowStudents)
                .map(ClassLessonEntity::getLessonEntity)
                .collect(Collectors.groupingBy(LessonEntity::getSubject))
                .entrySet().stream()
                .map(entry -> {
                    SubjectEntity subject = entry.getKey();
                    subject.setLessons(entry.getValue());
                    return subject;
                })
                .toList();
    }
}
