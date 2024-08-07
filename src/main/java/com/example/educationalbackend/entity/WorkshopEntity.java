package com.example.educationalbackend.entity;

import com.example.educationalbackend.entity.enums.WorkshopState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "workshops")
public class WorkshopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private SubjectEntity subject;

    Instant date;
    String school;
    String message;
    WorkshopState state;
    String stateInfo;
}
