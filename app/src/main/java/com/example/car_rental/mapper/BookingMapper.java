package com.example.car_rental.mapper;

import com.example.car_rental.dto.BookingReqDto;
import com.example.car_rental.dto.BookingRespDto;
import com.example.car_rental.model.Booking;

public class BookingMapper {
    public static Booking mapToEntity(BookingReqDto bookingReqDto) {
        Booking booking=new Booking();
        booking.setPickupDateTime(bookingReqDto.pickupDateTime());
        booking.setDropDateTime(bookingReqDto.dropDateTime());
        booking.setPickupLocation(bookingReqDto.pickupLocation());
        booking.setDropLocation(bookingReqDto.dropLocation());
        booking.setPaymentMethod(bookingReqDto.paymentMethod());
        return booking;
    }

    public static BookingRespDto mapToDto(Booking booking) {
        return new BookingRespDto(
                booking.getBookingId(),
                booking.getPickupDateTime(),
                booking.getDropDateTime(),
                booking.getPickupLocation(),
                booking.getDropLocation(),
                booking.getTotalAmount(),
                booking.getStatus(),
                booking.getCar().getCarNumber(),
                booking.getCar().getModel(),
                booking.getBuyer().getName(),
                booking.getPaymentMethod()
        );
    }
}
