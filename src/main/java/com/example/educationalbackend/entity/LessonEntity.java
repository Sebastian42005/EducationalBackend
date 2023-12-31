package com.example.educationalbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lessons")
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String name;
    byte[] teacherPDFContent;
    String teacherPDFContentType;
    byte[] studentPDFContent;
    String studentPDFContentType;

    @ManyToOne
    @JsonBackReference("subject-lessons")
    SubjectEntity subject;
}
