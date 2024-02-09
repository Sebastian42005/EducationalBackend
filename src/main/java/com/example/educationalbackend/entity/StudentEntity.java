package com.example.educationalbackend.entity;

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
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_teacher",
            joinColumns = { @JoinColumn(name = "teacher_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "id") })
    private List<TeacherEntity> teachers;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<SubjectEntity> subjects;
}
