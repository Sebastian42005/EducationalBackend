package com.example.educationalbackend.controller;

import com.example.educationalbackend.entity.LessonEntity;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.service.LessonService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping("/{id}/files")
    public Map<String, String> setLessonFiles(@PathVariable int id,
                                              @RequestParam("files") List<MultipartFile> files,
                                              @RequestParam("teacherOnlyList") List<Boolean> teacherOnlyList) throws EntityNotFoundException {
        lessonService.setLessonFiles(id, files, teacherOnlyList);
        return Map.of("message", "Files added successfully");
    }
    
    @PutMapping
    public LessonEntity updateLesson(@RequestBody LessonEntity lessonEntity) {
        return lessonService.updateLesson(lessonEntity);
    }

    @GetMapping
    public List<LessonEntity> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    public LessonEntity getLessonById(@PathVariable int id) throws EntityNotFoundException {
        return lessonService.getLessonById(id);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteLesson(@PathVariable int id) {
        lessonService.deleteLesson(id);
        return Map.of("message", "Lesson deleted successfully");
    }
}
