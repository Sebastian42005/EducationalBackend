package com.example.educationalbackend.controller;

import com.example.educationalbackend.dto.WorkshopStateChangeDto;
import com.example.educationalbackend.entity.MessageEntity;
import com.example.educationalbackend.entity.WorkshopEntity;
import com.example.educationalbackend.service.UserService;
import com.example.educationalbackend.service.WorkshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/workshop")
@RequiredArgsConstructor
public class WorkshopController {

    private final WorkshopService workshopService;
    private final UserService userService;

    @PostMapping("/book")
    public ResponseEntity<WorkshopEntity> bookWorkshop(@RequestBody WorkshopEntity workshop, Principal principal) {
        return ResponseEntity.ok(workshopService.bookWorkshop(workshop, principal));
    }

    @PutMapping("/change-state/{id}/{accepted}")
    public ResponseEntity<WorkshopEntity> changeWorkshopState(@PathVariable int id, @PathVariable boolean accepted) {
        return ResponseEntity.ok(workshopService.changeWorkshopState(id, accepted));
    }

    @GetMapping("/")
    public ResponseEntity<List<WorkshopEntity>> getAllWorkshops() {
        return ResponseEntity.ok(workshopService.getAllWorkshops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkshopEntity> getWorkshop(@PathVariable int id) {
        return ResponseEntity.ok(workshopService.getWorkshop(id));
    }

    @GetMapping("/own")
    public ResponseEntity<List<WorkshopEntity>> getOwnWorkshops(Principal principal) {
        return ResponseEntity.ok(workshopService.getOwnWorkshops(principal));
    }

    @PostMapping("/{id}/message")
    public ResponseEntity<MessageEntity> sendMessage(@PathVariable int id, @RequestBody String message, Principal principal) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage(message);
        messageEntity.setSender(userService.getOwnUser(principal.getName()));
        return ResponseEntity.ok(workshopService.sendMessage(id, messageEntity));
    }
}
