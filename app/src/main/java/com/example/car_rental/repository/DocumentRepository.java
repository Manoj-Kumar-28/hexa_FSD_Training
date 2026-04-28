package com.example.car_rental.repository;

import com.example.car_rental.model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Documents,Long> {
}
