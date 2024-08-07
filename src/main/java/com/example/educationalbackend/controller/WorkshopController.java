package com.example.educationalbackend.controller;

import com.example.educationalbackend.dto.WorkshopStateChangeDto;
import com.example.educationalbackend.entity.WorkshopEntity;
import com.example.educationalbackend.service.WorkshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workshop")
@RequiredArgsConstructor
public class WorkshopController {

    private final WorkshopService workshopService;

    @PostMapping("/book")
    public ResponseEntity<WorkshopEntity> bookWorkshop(@RequestBody WorkshopEntity workshop) {
        return ResponseEntity.ok(workshopService.bookWorkshop(workshop));
    }

    @PutMapping("/change-state/{id}")
    public ResponseEntity<WorkshopEntity> changeWorkshopState(@PathVariable int id, @RequestBody WorkshopStateChangeDto workshopStateChangeDto) {
        return ResponseEntity.ok(workshopService.changeWorkshopState(id, workshopStateChangeDto.isAccepted(), workshopStateChangeDto.getStateInfo()));
    }

    @GetMapping("/")
    public ResponseEntity<List<WorkshopEntity>> getAllWorkshops() {
        return ResponseEntity.ok(workshopService.getAllWorkshops());
    }
}
