package com.example.car_rental.service;

import com.example.car_rental.dto.ReviewReqDto;
import com.example.car_rental.dto.ReviewResDto;
import com.example.car_rental.exception.ResourceNotFoundException;
import com.example.car_rental.mapper.ReviewMapper;
import com.example.car_rental.model.Car;
import com.example.car_rental.model.Review;
import com.example.car_rental.model.User;
import com.example.car_rental.repository.CarRepository;
import com.example.car_rental.repository.ReviewRepository;
import com.example.car_rental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final ReviewRepository reviewRepository;

    public void addReview(ReviewReqDto reviewReqDto, long userId, long carId) {

        //add user and car by their id
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        Car car=carRepository.findById(carId)
                .orElseThrow(()->new ResourceNotFoundException("Car not found"));
        //convert dto to review
        Review review= ReviewMapper.mapTo(reviewReqDto);
        //save user and car
        review.setUser(user);
        review.setCar(car);
        //save review
        reviewRepository.save(review);
    }

    public List<ReviewResDto> getReviewsByCarId(long carId) {
        List<Review> list=reviewRepository.getReviewsByCarId(carId);
        return list
                .stream()
                .map(ReviewMapper::mapToDto)
                .toList();
    }
}
