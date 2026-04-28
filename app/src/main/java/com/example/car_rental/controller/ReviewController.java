package com.example.car_rental.controller;

import com.example.car_rental.dto.ReviewReqDto;
import com.example.car_rental.dto.ReviewResDto;
import com.example.car_rental.model.Review;
import com.example.car_rental.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add/{userId}/{carId}")
    public ResponseEntity<?> addReview(@RequestBody ReviewReqDto reviewReqDto,
                            @PathVariable long userId,
                            @PathVariable long carId) {
        reviewService.addReview(reviewReqDto,userId,carId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get/{carId}")
    public List<ReviewResDto> getReviewsByCarId(@PathVariable long carId) {
        return reviewService.getReviewsByCarId(carId);
    }
}
