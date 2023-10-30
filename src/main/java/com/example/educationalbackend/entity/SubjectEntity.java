package com.example.educationalbackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "subjects")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String name;

    byte[] image;
    String imageType;

    @OneToMany(mappedBy = "subject")
    @JsonManagedReference("subject-lessons")
    private List<LessonEntity> lessons;
}
