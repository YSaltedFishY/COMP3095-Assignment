package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest bookingRequest);

    List<BookingResponse> getAllBookings();

    List<BookingResponse> getUserBookings(String UserId);

    String UpdateBooking(String bookingId, BookingRequest bookingRequest);

    BookingResponse deleteBooking(String bookingId);


}
