package com.example.educationalbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
public record AuthenticationRequest (String email, String password) {
}
