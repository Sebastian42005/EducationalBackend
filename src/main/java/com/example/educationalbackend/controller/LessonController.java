package com.example.educationalbackend.controller;

import com.example.educationalbackend.entity.FileEntity;
import com.example.educationalbackend.entity.LessonEntity;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.service.LessonService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/{subjectId}")
    public LessonEntity createLesson(@RequestBody LessonEntity lessonEntity, @PathVariable int subjectId) throws EntityNotFoundException {
        return lessonService.createLesson(lessonEntity, subjectId);
    }

    @PutMapping("/{id}/pdf")
    public Map<String, String> setLessonPDFs(@PathVariable int id,
                                             @RequestParam("studentFile") MultipartFile studentFile,
                                             @RequestParam("teacherFile") MultipartFile teacherFile) throws EntityNotFoundException, IOException {
        lessonService.setLessonPDFs(id, studentFile, teacherFile);
        return Map.of("message", "Image added successfully");
    }

    @GetMapping
    public List<LessonEntity> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    public LessonEntity getLessonById(@PathVariable int id) throws EntityNotFoundException {
        return lessonService.getLessonById(id);
    }

    @GetMapping("/{id}/student-pdf")
    public ResponseEntity<byte[]> getStudentPDF(@PathVariable int id) throws EntityNotFoundException {
        FileEntity file = lessonService.getStudentPDF(id);
        return ResponseEntity.ok().headers(getHeaders()).contentType(MediaType.valueOf(file.getFileType())).body(file.getContent());
    }

    @GetMapping("/{id}/teacher-pdf")
    public ResponseEntity<byte[]> getTeacherPDF(@PathVariable int id) throws EntityNotFoundException {
        FileEntity file = lessonService.getTeacherPDF(id);
        return ResponseEntity.ok().headers(getHeaders()).contentType(MediaType.valueOf(file.getFileType())).body(file.getContent());
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Frame-Options", "ALLOW-FROM *");
        return headers;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteLesson(@PathVariable int id) {
        lessonService.deleteLesson(id);
        return Map.of("message", "Lesson deleted successfully");
    }
}
