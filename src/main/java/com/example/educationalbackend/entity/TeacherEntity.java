package com.example.educationalbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private int id;

    @ManyToMany(mappedBy = "teachers")
    private List<StudentEntity> students = new ArrayList<>();

    @ManyToMany(mappedBy = "teachers")
    private List<SubjectEntity> subjects = new ArrayList<>();

    @Override
    public String toString() {
        return "TeacherEntity{" +
                "id=" + id +
                ", students=" + students.stream().map(StudentEntity::getId).toList() +
                ", subjects=" + subjects.stream().map(SubjectEntity::getName).toList() +
                '}';
    }
}
