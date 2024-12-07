package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.client.RoomClient;
import ca.gbc.bookingservice.client.UserClient;
import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.event.BookingMadeEvent;
import ca.gbc.bookingservice.event.UserInfoEvent;
import ca.gbc.bookingservice.model.Booking;
import ca.gbc.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
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
    private final KafkaTemplate<String, BookingMadeEvent> kafkaTemplate;
    private final JavaMailSender javaMailSender;
    private UserInfoEvent userInfoEvent;

    @KafkaListener(topics = "user-info")
    public void listen(UserInfoEvent event){
        this.userInfoEvent = event;
    }

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {

        boolean userExist = userClient.getUserInfo(bookingRequest.userId());

        var isRoomAvailable = roomClient.getRoomAvailability(bookingRequest.roomId());

        if(isRoomAvailable && userExist) {
            Booking booking = Booking.builder()
                    .userId(bookingRequest.userId())
                    .roomId(bookingRequest.roomId())
                    .startTime(bookingRequest.startTime())
                    .endTime(bookingRequest.endTime())
                    .purpose(bookingRequest.purpose())
                    .email(userInfoEvent.getEmail())
                    .name(userInfoEvent.getName())
                    .role(userInfoEvent.getRole())
                    .build();

            bookingRepository.save(booking);

            roomClient.updateAvailability(bookingRequest.roomId(), false);

            //Send message to Kafka
            BookingMadeEvent bookingMadeEvent =
                    new BookingMadeEvent(booking.getId(),booking.getUserId(),booking.getRoomId(),
                            booking.getStartTime(),booking.getEndTime(),booking.getName(),
                            booking.getEmail(),booking.getRole());

            log.info("Start - Sending BookingMadeEvent {} to Kafka topic booking-made", bookingMadeEvent);
            kafkaTemplate.send("booking-made", bookingMadeEvent);
            log.info("Complete - Sent BookingMadeEvent {} to Kafka topic booking-made", bookingMadeEvent);



            return new BookingResponse(booking.getId(), booking.getUserId(), booking.getRoomId(),
                    booking.getStartTime(), booking.getEndTime(), booking.getPurpose(),
                    booking.getEmail(),booking.getName(),booking.getRole());
        }else{
            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setTo(userInfoEvent.getEmail());
                messageHelper.setSubject("Your Booking was not made");
                messageHelper.setText(String.format("""
                                        
                    Dear %1$s, 
                    Your Booking for Room %2$s was not successfully approved due to scheduling conflicts. 
                                        
                    COMP3095 Staff
                                        
                                        
                    """, userInfoEvent.getName(), bookingRequest.roomId()));
            };


            try {
                javaMailSender.send(messagePreparator);
                log.info("Booking Notif successfully sent");
            } catch (MailException e) {
                log.error("Exception occurred when sending mail", e);
            }

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
                booking.getStartTime(),booking.getEndTime(), booking.getPurpose(),booking.getEmail(),
                booking.getName(),booking.getRole());
    }

    @Override
    public String updateBooking(String id, BookingRequest bookingRequest) {
        boolean userExist = userClient.getUserInfo(bookingRequest.userId());
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Booking booking = mongoTemplate.findOne(query, Booking.class);

        if(booking != null){

            booking.setRoomId(bookingRequest.roomId());
            booking.setStartTime(bookingRequest.startTime());
            booking.setEndTime(bookingRequest.endTime());
            booking.setPurpose(bookingRequest.purpose());
            if(userExist){
                booking.setUserId(bookingRequest.userId());
                booking.setName(userInfoEvent.getName());
                booking.setEmail(userInfoEvent.getEmail());
                booking.setRole(userInfoEvent.getRole());
            }

            return bookingRepository.save(booking).getId();
        }



        return id;
    }

    @Override
    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }
}
