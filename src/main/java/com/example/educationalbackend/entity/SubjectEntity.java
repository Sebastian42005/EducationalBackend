package com.example.educationalbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
@Entity
@Table(name = "subjects")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String description;
    int file;
    boolean free;

    @OneToMany(mappedBy = "subject")
    @JsonManagedReference("subject-lessons")
    private List<LessonEntity> lessons;

    @ManyToMany
    @JoinTable(name = "teacher_subject",
            joinColumns = { @JoinColumn(name = "subject_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "teacher_id", referencedColumnName = "id") })
    @JsonIgnore
    private List<TeacherEntity> teachers;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    private List<WorkshopEntity> workshops;
}
