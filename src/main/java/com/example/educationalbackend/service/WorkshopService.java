package com.example.educationalbackend.service;

import com.example.educationalbackend.entity.WorkshopEntity;
import com.example.educationalbackend.entity.enums.WorkshopState;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.repository.WorkshopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkshopService {

    private final WorkshopRepository workshopRepository;

    public WorkshopEntity bookWorkshop(WorkshopEntity workshop) {
        workshop.setId(0);
        workshop.setState(WorkshopState.PENDING);
        return workshopRepository.save(workshop);
    }

    public WorkshopEntity changeWorkshopState(int id, boolean accepted, String stateInfo) {
        WorkshopEntity workshop = workshopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.WORKSHOP, id));
        workshop.setState(accepted ? WorkshopState.ACCEPTED : WorkshopState.REJECTED);
        workshop.setStateInfo(stateInfo);
        return workshopRepository.save(workshop);
    }

    public List<WorkshopEntity> getAllWorkshops() {
        return workshopRepository.findAll();
    }
}
