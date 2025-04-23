package com.example.educationalbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "classes")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    String description;

    @Column(name = "room_id", length = 6)
    Integer roomCode;

    LocalDateTime expiresAt;

    @ManyToMany
    @JoinTable(name = "class_students",
            joinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "class_id", referencedColumnName = "id") })
    @JsonIgnore
    List<StudentEntity> students = new ArrayList<>();

    @ManyToOne
    TeacherEntity teacher;

    @OneToMany(mappedBy = "classEntity")
    List<ClassLessonEntity> lessons;
}
