package com.example.educationalbackend.controller;

import com.example.educationalbackend.entity.LessonEntity;
import com.example.educationalbackend.exception.EntityNotFoundException;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.repository.LessonRepository;
import com.example.educationalbackend.repository.SubjectRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;

    public LessonController(LessonRepository lessonRepository, SubjectRepository subjectRepository) {
        this.lessonRepository = lessonRepository;
        this.subjectRepository = subjectRepository;
    }

    @PostMapping("/{subjectId}")
    public LessonEntity createLesson(@RequestBody LessonEntity lessonEntity, @PathVariable int subjectId) throws EntityNotFoundException {
        lessonEntity.setSubject(subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException(EntityType.SUBJECT, subjectId)));
        return lessonRepository.save(lessonEntity);
    }

    @PutMapping("/{id}/pdf")
    public Map<String, String> setLessonPDFs(@PathVariable int id,
                                             @RequestParam("studentFile") MultipartFile studentFile,
                                             @RequestParam("teacherFile") MultipartFile teacherFile) throws EntityNotFoundException, IOException {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, id));
        lessonEntity.setStudentPDFContent(studentFile.getBytes());
        lessonEntity.setStudentPDFContentType(studentFile.getContentType());
        lessonEntity.setTeacherPDFContent(teacherFile.getBytes());
        lessonEntity.setTeacherPDFContentType(teacherFile.getContentType());
        lessonRepository.save(lessonEntity);
        return Map.of("message", "Image added successfully");
    }

    @GetMapping
    public List<LessonEntity> getAllLessons() {
        return lessonRepository.findAll();
    }

    @GetMapping("/{id}")
    public LessonEntity getLessonById(@PathVariable int id) throws EntityNotFoundException {
        return lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, id));
    }

    @GetMapping("/{id}/student-pdf")
    public ResponseEntity<byte[]> getStudentPDF(@PathVariable int id) throws EntityNotFoundException {
        LessonEntity lesson = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, id));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Frame-Options", "ALLOW-FROM *");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(lesson.getStudentPDFContent());
    }

    @GetMapping("/{id}/teacher-pdf")
    public ResponseEntity<byte[]> getTeacherPDF(@PathVariable int id) throws EntityNotFoundException {
        LessonEntity lesson = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, id));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Frame-Options", "ALLOW-FROM *");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(lesson.getTeacherPDFContent());
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteLesson(@PathVariable int id) {
        lessonRepository.deleteById(id);
        return Map.of("message", "Lesson deleted successfully");
    }
}
