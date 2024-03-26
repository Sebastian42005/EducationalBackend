package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.FileEntity;
import com.example.educationalbackend.entity.LessonEntity;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.repository.FileRepository;
import com.example.educationalbackend.repository.LessonRepository;
import com.example.educationalbackend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;
    private final FileRepository fileRepository;

    public LessonEntity createLesson(LessonEntity lessonEntity, int subjectId) {
        lessonEntity.setSubject(subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException(EntityType.SUBJECT, subjectId)));
        return lessonRepository.save(lessonEntity);
    }

    public void setLessonPDFs(int id, MultipartFile studentFile, MultipartFile teacherFile) throws IOException {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, id));
        FileEntity studentFileEntity = new FileEntity(studentFile.getBytes(), studentFile.getContentType());
        FileEntity teacherFileEntity = new FileEntity(teacherFile.getBytes(), teacherFile.getContentType());
        fileRepository.save(studentFileEntity);
        fileRepository.save(teacherFileEntity);
        lessonEntity.setStudentPDF(studentFileEntity.getId());
        lessonEntity.setTeacherPDF(teacherFileEntity.getId());
        lessonRepository.save(lessonEntity);
    }

    public List<LessonEntity> getAllLessons() {
        return lessonRepository.findAll();
    }

    public LessonEntity getLessonById(int id) {
        return lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, id));
    }

    public FileEntity getStudentPDF(int id) {
        LessonEntity lesson = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, id));
        return fileRepository.findById(lesson.getStudentPDF()).orElseThrow(() -> new EntityNotFoundException(EntityType.FILE, id));
    }

    public FileEntity getTeacherPDF(int id) {
        LessonEntity lesson = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, id));
        return fileRepository.findById(lesson.getTeacherPDF()).orElseThrow(() -> new EntityNotFoundException(EntityType.FILE, id));
    }

    public void deleteLesson(int id) {
        lessonRepository.deleteById(id);
    }
}
