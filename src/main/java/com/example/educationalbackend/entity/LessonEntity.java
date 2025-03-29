package com.example.educationalbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lessons")
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;

    String description;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.REMOVE)
    List<FileEntity> files;

    @ManyToOne
    @JsonBackReference("subject-lessons")
    SubjectEntity subject;
}
