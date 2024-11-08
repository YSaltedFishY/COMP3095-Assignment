package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest bookingRequest);

    List<BookingResponse> getAllBookings(BookingRequest bookingRequest);

    List<BookingResponse> getUserBookings(String UserId);

    BookingResponse UpdateBooking(String bookingId, BookingRequest bookingRequest);

    BookingResponse deleteBooking(String bookingId);


}
