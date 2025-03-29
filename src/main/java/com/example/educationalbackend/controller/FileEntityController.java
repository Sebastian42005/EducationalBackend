package com.example.educationalbackend.controller;

import com.example.educationalbackend.entity.FileEntity;
import com.example.educationalbackend.exception.enums.EntityType;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.repository.FileRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class FileEntityController {

    private final FileRepository fileRepository;

    public FileEntityController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable int id) throws EntityNotFoundException {
        FileEntity file = fileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EntityType.FILE, id));
        return ResponseEntity.ok().headers(getHeaders()).contentType(MediaType.valueOf(file.getFileType())).body(file.getContent());
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Frame-Options", "ALLOW-FROM *");
        return headers;
    }
}
