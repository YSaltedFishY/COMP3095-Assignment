package ca.gbc.bookingservice.service;


import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.model.Booking;
import ca.gbc.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;




    @Override
    public boolean isRoomAvailable(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> dupBooking = bookingRepository.findByRoomIdAndStartTimeBeforeAndEndTime(roomId, startTime, endTime);
        return dupBooking.isEmpty();
    }

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        if (isRoomAvailable(bookingRequest.roomId(), bookingRequest.startTime(), bookingRequest.endTime())) {
            Booking booking = new Booking();
            booking.setUserId(bookingRequest.UserId());
            booking.setRoomId(bookingRequest.roomId());
            booking.setStartTime(bookingRequest.startTime());
            booking.setEndTime(bookingRequest.endTime());
            booking.setPurpose(bookingRequest.purpose());

            Booking newBooking = bookingRepository.save(booking);

            return new BookingResponse(newBooking.getBookingId(), newBooking.getUserId(), newBooking.getRoomId(),
                    newBooking.getStartTime(), newBooking.getEndTime(), newBooking.getPurpose());
        } else {
            throw new IllegalStateException("Room is already booked for the given time slot.");
        }
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(this::mapToBookingResponse).toList();
    }

    @Override
    public Optional<BookingResponse> getBookingById(Long id) {
        return bookingRepository.findById(id).map(this::mapToBookingResponse);
    }

    private BookingResponse mapToBookingResponse(Booking booking) {
        return new BookingResponse(booking.getBookingId(), booking.getUserId(), booking.getRoomId(),
                booking.getStartTime(), booking.getEndTime(), booking.getPurpose());
    }
}
