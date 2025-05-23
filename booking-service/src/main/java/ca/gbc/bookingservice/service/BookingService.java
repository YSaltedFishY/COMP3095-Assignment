package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);

    List<BookingResponse> getAllBookings();

    String updateBooking(String id, BookingRequest bookingRequest);

    void deleteBooking(String id);
}
