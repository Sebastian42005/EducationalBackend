package com.example.educationalbackend.controller;

import com.example.educationalbackend.entity.ClassEntity;
import com.example.educationalbackend.entity.SubjectEntity;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/class")
public class ClassController {

    private final ClassService classService;

    @PutMapping("/join-class/{roomId}")
    public ResponseEntity<ClassEntity> joinClass(@PathVariable int roomId, Principal principal) {
        return ResponseEntity.ok(classService.joinClass(roomId, principal).orElseThrow(() -> new EntityNotFoundException(EntityType.CLASS, roomId)));
    }

    @PostMapping("/create-class")
    public ResponseEntity<ClassEntity> createClass(@RequestBody ClassEntity classEntity, Principal principal) {
        return ResponseEntity.ok(classService.createClass(classEntity.getName(), classEntity.getDescription(), principal));
    }

    @PostMapping("/create-room-code/{classId}")
    public ResponseEntity<Integer> createRoomCode(@PathVariable int classId) {
        return ResponseEntity.ok(classService.createRoomCode(classId));
    }

    @GetMapping("/get-student-subjects")
    public ResponseEntity<List<SubjectEntity>> getStudentSubjects(Principal principal) {
        return ResponseEntity.ok(classService.getStudentSubjects(principal));
    }
}
