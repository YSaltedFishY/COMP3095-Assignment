package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    boolean isRoomAvailable(Long roomId, LocalDateTime startTime, LocalDateTime endTime);

    BookingResponse createBooking(BookingRequest bookingRequest);

    List<BookingResponse> getAllBookings();

    Optional<BookingResponse> getBookingById(Long id);
}
