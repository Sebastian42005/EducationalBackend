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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;

    byte[] image;
    String imageType;

    @OneToMany(mappedBy = "subject")
    @JsonManagedReference("subject-lessons")
    private List<LessonEntity> lessons;

    @ManyToMany
    @JoinTable(name = "teacher_subject",
            joinColumns = { @JoinColumn(name = "teacher_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "subject_id", referencedColumnName = "id") })
    private List<TeacherEntity> teachers;
}
