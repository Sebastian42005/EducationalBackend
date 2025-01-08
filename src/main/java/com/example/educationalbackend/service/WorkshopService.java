package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.MessageEntity;
import com.example.educationalbackend.entity.WorkshopEntity;
import com.example.educationalbackend.entity.enums.WorkshopState;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.repository.MessageRepository;
import com.example.educationalbackend.repository.WorkshopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkshopService {

    private final WorkshopRepository workshopRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;

    public WorkshopEntity bookWorkshop(WorkshopEntity workshop, Principal principal) {
        workshop.setId(0);
        workshop.setState(WorkshopState.PENDING);
        workshop.setSender(userService.getOwnUser(principal.getName()));
        return workshopRepository.save(workshop);
    }

    public WorkshopEntity changeWorkshopState(int id, boolean accepted) {
        WorkshopEntity workshop = workshopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.WORKSHOP, id));
        workshop.setState(accepted ? WorkshopState.ACCEPTED : WorkshopState.REJECTED);
        return workshopRepository.save(workshop);
    }

    public List<WorkshopEntity> getAllWorkshops() {
        return workshopRepository.findAll();
    }

    public List<WorkshopEntity> getOwnWorkshops(Principal principal) {
        return workshopRepository.findBySender(principal.getName());
    }

    public MessageEntity sendMessage(int id, MessageEntity message) {
        WorkshopEntity workshop = workshopRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workshop not found"));
        message.setWorkshop(workshop);
        return messageRepository.save(message);
    }

    public WorkshopEntity getWorkshop(int id) {
        return workshopRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workshop not found"));
    }
}
