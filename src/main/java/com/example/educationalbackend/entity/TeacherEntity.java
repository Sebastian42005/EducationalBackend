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
@Table(name = "teachers")
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
    private List<StudentEntity> students;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<SubjectEntity> subjects;
}
