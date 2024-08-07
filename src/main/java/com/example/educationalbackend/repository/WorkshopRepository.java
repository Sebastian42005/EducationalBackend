package com.example.educationalbackend.repository;

import com.example.educationalbackend.entity.WorkshopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository  extends JpaRepository<WorkshopEntity, Integer> {
}
