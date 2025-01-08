package com.example.educationalbackend.entity;

import com.example.educationalbackend.entity.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "message")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;
    @ManyToOne(cascade = CascadeType.MERGE)
    private UserEntity sender;
    private Instant date = Instant.now();
    private MessageType type;
    @JsonIgnore
    @ManyToOne
    private WorkshopEntity workshop;
}
