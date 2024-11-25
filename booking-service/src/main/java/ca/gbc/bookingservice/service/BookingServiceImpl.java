package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.client.RoomClient;
import ca.gbc.bookingservice.client.UserClient;
import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.model.Booking;
import ca.gbc.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final MongoTemplate mongoTemplate;
    private final UserClient userClient;
    private final RoomClient roomClient;

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {

        var isRoomAvailable = roomClient.getRoomAvailability(bookingRequest.roomId());

        if(isRoomAvailable) {
            Booking booking = Booking.builder()
                    .userId(bookingRequest.userId())
                    .roomId(bookingRequest.roomId())
                    .startTime(bookingRequest.startTime())
                    .endTime(bookingRequest.endTime())
                    .purpose(bookingRequest.purpose())
                    .build();

            bookingRepository.save(booking);

            roomClient.updateAvailability(bookingRequest.roomId(), false);

            return new BookingResponse(booking.getId(), booking.getUserId(), booking.getRoomId(),
                    booking.getStartTime(), booking.getEndTime(), booking.getPurpose());
        }else{
            throw new RuntimeException("Room id " + bookingRequest.roomId() + " is not available");
        }
    }

    @Override
    public List<BookingResponse> getAllBookings() {

        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map(this::mapToBookingResponse).toList();
    }

    private BookingResponse mapToBookingResponse(Booking booking){
        return new BookingResponse(booking.getId(), booking.getUserId(), booking.getRoomId(),
                booking.getStartTime(),booking.getEndTime(), booking.getPurpose());
    }

    @Override
    public String updateBooking(String id, BookingRequest bookingRequest) {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Booking booking = mongoTemplate.findOne(query, Booking.class);

        if(booking != null){
            booking.setUserId(bookingRequest.userId());
            booking.setRoomId(bookingRequest.roomId());
            booking.setStartTime(bookingRequest.startTime());
            booking.setEndTime(bookingRequest.endTime());
            booking.setPurpose(bookingRequest.purpose());
            return bookingRepository.save(booking).getId();
        }

        return id;
    }

    @Override
    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }
}
