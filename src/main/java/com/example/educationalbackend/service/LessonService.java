package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.FileEntity;
import com.example.educationalbackend.entity.LessonEntity;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.repository.FileRepository;
import com.example.educationalbackend.repository.LessonRepository;
import com.example.educationalbackend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
        lessonEntity.setStudentPDFName(studentFile.getOriginalFilename());
        lessonEntity.setTeacherPDF(teacherFileEntity.getId());
        lessonEntity.setTeacherPDFName(teacherFile.getOriginalFilename());
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

    @Transactional
    public LessonEntity updateLesson(LessonEntity lessonEntity) {
        LessonEntity dbLesson = lessonRepository.findById(lessonEntity.getId()).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, lessonEntity.getId()));
        dbLesson.setName(lessonEntity.getName());
        dbLesson.setStudentPDFName(lessonEntity.getStudentPDFName());
        dbLesson.setTeacherPDFName(lessonEntity.getTeacherPDFName());
        return dbLesson;
    }

    @Transactional
    public void updateLessonPDFs(int id, MultipartFile studentFile, MultipartFile teacherFile) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, id));
        fileRepository.findById(lessonEntity.getStudentPDF()).ifPresent(fileEntity -> {
            if (studentFile != null) {
                lessonEntity.setStudentPDFName(studentFile.getOriginalFilename());
                fileEntity.setFileType(studentFile.getContentType());
                try {
                    fileEntity.setContent(studentFile.getBytes());
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            }
        });
        fileRepository.findById(lessonEntity.getTeacherPDF()).ifPresent(fileEntity -> {
            if (teacherFile != null) {
                lessonEntity.setTeacherPDFName(teacherFile.getOriginalFilename());
                fileEntity.setFileType(teacherFile.getContentType());
                try {
                    fileEntity.setContent(teacherFile.getBytes());
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            }
        });
    }
}
