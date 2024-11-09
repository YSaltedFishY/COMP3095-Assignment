package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.client.RoomServiceClient;
import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.model.Booking;
import ca.gbc.roomservice.dto.RoomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
                .bookingId(bookingRequest.bookingId())
                .userId(bookingRequest.userId())
                .roomId(bookingRequest.roomId())
                .startTime(bookingRequest.startTime())
                .endTime(bookingRequest.endTime())
                .purpose(bookingRequest.purpose())
                .build();
        Booking savedBooking = bookingRepository.save(booking);

        return new BookingResponse(savedBooking.getBookingId(), savedBooking.getUserId(), savedBooking.getUserId(),
                savedBooking.getEndTime(), savedBooking.getStartTime(), savedBooking.getPurpose());
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        log.debug("Return a list of bookings");
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(this::mapToBookingResponse).toList();

    }

    private BookingResponse mapToBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getBookingId(),
                booking.getUserId(),
                booking.getRoomId(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getPurpose());
    }

    @Override
    public List<BookingResponse> getUserBookings(String UserId) {
        //TODO
        return null;
    }

    @Override
    public String UpdateBooking(String bookingId, BookingRequest bookingRequest) {
        log.debug("Updating product");

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(bookingId));
        Booking booking = mongoTemplate.findOne(query, Booking.class);

        if (booking != null) {
            booking.setBookingId(bookingRequest.bookingId());
            booking.setUserId(bookingRequest.userId()); //Could be changed
            booking.setRoomId(bookingRequest.roomId());
            booking.setStartTime(bookingRequest.startTime());
            booking.setEndTime(bookingRequest.endTime());
            booking.setPurpose(bookingRequest.purpose());
            log.info("Updated Product");
            return bookingRepository.save(booking).getBookingId();
        }
        return bookingId;
    }

    @Override
    public BookingResponse deleteBooking(String bookingId) {
        return null;
    }
}




