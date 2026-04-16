package com.example.car_rental.mapper;

import com.example.car_rental.dto.ReviewReqDto;
import com.example.car_rental.dto.ReviewResDto;
import com.example.car_rental.model.Review;

public class ReviewMapper {
    public static Review mapTo(ReviewReqDto reviewReqDto) {
        Review review=new Review();
        review.setRating(reviewReqDto.rating());
        review.setComment(reviewReqDto.comment());
        return review;
    }

    public static ReviewResDto mapToDto(Review review) {
        return new ReviewResDto(
                review.getRating(),
                review.getComment()
        );
    }
}
