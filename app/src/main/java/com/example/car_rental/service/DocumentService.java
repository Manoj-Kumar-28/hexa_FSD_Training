package com.example.car_rental.service;

import com.example.car_rental.dto.DocDto;
import com.example.car_rental.model.Car;
import com.example.car_rental.model.Documents;
import com.example.car_rental.repository.CarRepository;
import com.example.car_rental.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
@Slf4j
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final CarRepository carRepository;

    private final String UPLOAD_PATH="C:/Users/lenovo/Downloads/trs-ui/trs-ui/public/uploads";

    public DocDto upload(String name, Long carId, MultipartFile file) throws IOException {
        log.info("Uploading document");
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        // Optional but IMPORTANT: verify car belongs to user
        if (!car.getOwner().getUser().getUsername().equals(name)) {
            throw new RuntimeException("Unauthorized access to this car");
        }

        File directory=new File(UPLOAD_PATH);;

        String fileName =System.currentTimeMillis() + "_" +  file.getOriginalFilename();

        Path path= Paths.get(UPLOAD_PATH+"/"+fileName);

        Files.write(path,file.getBytes());

        Documents documents=new Documents();
        documents.setCar(car);
        documents.setCarImage(fileName);

        Documents docs=documentRepository.save(documents);
        log.info("Document uploaded successfully");
        return new DocDto(
          docs.getId(),
          docs.getCarImage(),
          docs.getCar().getId()
        );
    }
}
