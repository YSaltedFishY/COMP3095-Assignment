package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.model.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ca.gbc.bookingservice.repository.BookingRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
       //TODO: Validate

        // Create a new Booking object
        Booking booking = Booking.builder()
                .id(bookingRequest.bookingId())
                .userId(bookingRequest.UserId())
                .roomId(bookingRequest.roomId())
                .startTime(bookingRequest.startTime())
                .endTime(bookingRequest.endTime())
                .purpose(bookingRequest.purpose())
                .build();
        Booking savedBooking = bookingRepository.save(booking);

        return new BookingResponse(savedBooking.getId(), "SUCCESS", "Booking created successfully.");
    }

    @Override
    public List<BookingResponse> getAllBookings(BookingRequest bookingRequest) {
        return null;
    }
}
