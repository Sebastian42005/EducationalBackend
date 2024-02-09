package com.example.educationalbackend.repository;

import com.example.educationalbackend.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

}
