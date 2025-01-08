package com.example.educationalbackend.entity;

import com.example.educationalbackend.entity.enums.WorkshopState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    private Instant date;
    private String school;
    private WorkshopState state;
    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private List<MessageEntity> messages = new ArrayList<>();

    @ManyToOne
    private UserEntity sender;
}
