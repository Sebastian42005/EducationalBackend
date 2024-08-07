package com.example.educationalbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkshopStateChangeDto {
    private boolean accepted;
    private String stateInfo;
}
