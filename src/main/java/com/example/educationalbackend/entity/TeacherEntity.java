package com.example.educationalbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "teachers")
    private List<StudentEntity> students;

    @JsonIgnore
    @ManyToMany(mappedBy = "teachers")
    private List<SubjectEntity> subjects;

    @Override
    public String toString() {
        return "TeacherEntity{" +
                "id=" + id +
                ", students=" + students.stream().map(StudentEntity::getId).toList() +
                ", subjects=" + subjects.stream().map(SubjectEntity::getName).toList() +
                '}';
    }
}
