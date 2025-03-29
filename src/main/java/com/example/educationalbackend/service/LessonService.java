package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.FileEntity;
import com.example.educationalbackend.entity.LessonEntity;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.repository.FileRepository;
import com.example.educationalbackend.repository.LessonRepository;
import com.example.educationalbackend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Transactional
    public void setLessonFiles(int id, List<MultipartFile> files, List<Boolean> teacherOnlyList) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, id));
        fileRepository.deleteAll(lessonEntity.getFiles());
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            try {
                FileEntity fileEntity = new FileEntity(file.getOriginalFilename(), file.getBytes(), file.getContentType(), teacherOnlyList.get(i), lessonEntity);
                fileRepository.save(fileEntity);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }
    }

    public List<LessonEntity> getAllLessons() {
        return lessonRepository.findAll();
    }

    public LessonEntity getLessonById(int id) {
        return lessonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, id));
    }

    public void deleteLesson(int id) {
        lessonRepository.deleteById(id);
    }

    @Transactional
    public LessonEntity updateLesson(LessonEntity lessonEntity) {
        LessonEntity dbLesson = lessonRepository.findById(lessonEntity.getId()).orElseThrow(() -> new EntityNotFoundException(EntityType.LESSON, lessonEntity.getId()));
        dbLesson.setName(lessonEntity.getName());
        dbLesson.setDescription(lessonEntity.getDescription());
        return dbLesson;
    }
}
