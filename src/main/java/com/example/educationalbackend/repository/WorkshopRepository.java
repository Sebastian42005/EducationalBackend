package com.example.educationalbackend.repository;

import com.example.educationalbackend.entity.WorkshopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkshopRepository  extends JpaRepository<WorkshopEntity, Integer> {

    @Query("SELECT w FROM WorkshopEntity w WHERE w.sender.email = :email")
    List<WorkshopEntity> findBySender(String email);
}
