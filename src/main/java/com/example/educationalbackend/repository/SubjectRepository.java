package com.example.educationalbackend.repository;

import com.example.educationalbackend.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

    @Query("SELECT s from SubjectEntity s WHERE s.free = true")
    List<SubjectEntity> getAllFree();

    @Query("SELECT s from SubjectEntity s WHERE s.free = false")
    List<SubjectEntity> getAllNotFree();

    @Query("SELECT s FROM SubjectEntity s JOIN s.teachers t WHERE t.id = :teacherId")
    List<SubjectEntity> getTeacherSubjects(int teacherId);

}
