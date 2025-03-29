package com.example.educationalbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "files")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @JsonIgnore
    byte[] content;
    String fileType;
    String name;
    boolean teacherOnly = false;

    @ManyToOne
    @JsonIgnore
    LessonEntity lesson;

    public FileEntity(byte[] content, String fileType) {
        this.content = content;
        this.fileType = fileType;
    }

    public FileEntity(String name, byte[] content, String fileType, boolean teacherOnly, LessonEntity lesson) {
        this.name = name;
        this.content = content;
        this.fileType = fileType;
        this.teacherOnly = teacherOnly;
        this.lesson = lesson;
    }
}
