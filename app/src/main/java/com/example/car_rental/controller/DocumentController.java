package com.example.car_rental.controller;

import com.example.car_rental.dto.DocDto;
import com.example.car_rental.model.Documents;
import com.example.car_rental.service.DocumentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/document")
@CrossOrigin(origins = "http://localhost:5173/" )
@Slf4j
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/upload")
    public DocDto upload(Principal principal, @RequestParam Long carId, @RequestParam("file") MultipartFile file) throws IOException {
        String username=principal.getName();
        log.info("Document upload request received...");
        DocDto response = documentService.upload(username, carId, file);

        log.info("Document uploaded successfully...");

        return response;
    }
}
