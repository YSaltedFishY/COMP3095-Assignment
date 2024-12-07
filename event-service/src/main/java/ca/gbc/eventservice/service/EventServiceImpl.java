package ca.gbc.eventservice.service;


import ca.gbc.eventservice.client.BookingClient;
import ca.gbc.eventservice.client.UserClient;
import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.event.BookingMadeEvent;
import ca.gbc.eventservice.event.EventMadeEvent;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.repository.EventRepository;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final MongoTemplate mongoTemplate;
    private final BookingClient bookingClient;
    private final UserClient userClient;
    private final JavaMailSender javaMailSender;
    private final KafkaTemplate<String, EventMadeEvent> kafkaTemplate;

    @KafkaListener(topics = "booking-made")
    public void listen(BookingMadeEvent bookingMadeEvent){
        Event event = Event.builder()
                .eventName("Room Booking")
                .organizerId(bookingMadeEvent.getUserId())
                .roomId(bookingMadeEvent.getRoomId())
                .bookingId(bookingMadeEvent.getBookingNumber())
                .eventType("Booking")
                .expectedAttendees(10)
                .startTime(bookingMadeEvent.getStartTime())
                .endTime(bookingMadeEvent.getEndTime())
                .email(bookingMadeEvent.getEmail())
                .organizerRole(bookingMadeEvent.getRole())
                .organizerName(bookingMadeEvent.getOrganizerName())
                .approval("PENDING")
                .build();
        eventRepository.save(event);

        //Send to approval service
        EventMadeEvent eventMadeEvent =
                new EventMadeEvent(event.getId(),event.getOrganizerId(),
                        event.getOrganizerRole());
        log.info("Start - Sending EventMadeEvent {} to Kafka topic event-made", eventMadeEvent);
        kafkaTemplate.send("event-made", eventMadeEvent);
        log.info("Complete - Sent BookingMadeEvent {} to Kafka topic event-made", eventMadeEvent);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(bookingMadeEvent.getEmail());
            messageHelper.setSubject(String.format("Your Booking (%1$s) was placed successfully", bookingMadeEvent.getBookingNumber()));
            messageHelper.setText(String.format("""
                                        
                    Dear %2$s, 
                    Your Booking for room %3$s with Booking number %1$s has been successfully placed. 
                                        
                    An event has been made for the booking. Please click this link to fill in in your event details. 
                                        
                    Thank you for your business.
                    COMP3095 Staff
                                        
                                        
                    """, bookingMadeEvent.getBookingNumber(), bookingMadeEvent.getOrganizerName(), bookingMadeEvent.getRoomId()));
        };


        try {
            javaMailSender.send(messagePreparator);
            log.info("Booking Notif successfully sent");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
        }
    }



    @Override
    public EventResponse createEvent(EventRequest eventRequest) {

        var isUserStaff = userClient.checkIfUserStaff(eventRequest.organizerId());

        if(isUserStaff) {
            Event event = Event.builder()
                    .eventName(eventRequest.eventName())
                    .organizerId(eventRequest.organizerId())
                    .roomId(eventRequest.roomId())
                    .bookingId(eventRequest.bookingId())
                    .eventType(eventRequest.eventType())
                    .expectedAttendees(eventRequest.expectedAttendees())
                    .startTime(eventRequest.startTime())
                    .endTime(eventRequest.endTime())
                    .email(eventRequest.email())
                    .organizerRole(eventRequest.organizerRole())
                    .organizerName(eventRequest.organizerName())
                    .approval("PENDING")
                    .build();
            eventRepository.save(event);

            return new EventResponse(event.getId(),event.getBookingId(),event.getRoomId(),
                    event.getOrganizerId(),event.getOrganizerName(),event.getOrganizerRole(),
                    event.getEventName(),event.getEventType(),event.getExpectedAttendees(),
                    event.getStartTime(),event.getEndTime(),event.getEmail(),event.getApproval());
        }else if (!isUserStaff && eventRequest.expectedAttendees()>10) {
            throw new RuntimeException("ExpectedAttendees:" + eventRequest.expectedAttendees()
                    + " Students may only have up to 10 persons for event booking");
        }else{
            throw new RuntimeException("UnknownError has occurred");
        }
    }

    @Override
    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();

        return events.stream().map(this::mapToEventResponse).toList();
    }

    private EventResponse mapToEventResponse(Event event){
        return new EventResponse(event.getId(),event.getBookingId(),event.getRoomId(),
                event.getOrganizerId(),event.getOrganizerName(),event.getOrganizerRole(),
                event.getEventName(),event.getEventType(),event.getExpectedAttendees(),
                event.getStartTime(),event.getEndTime(),event.getEmail(),event.getApproval());
    }

    @Override
    public String updateEvent(String id, EventRequest eventRequest) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Event event = mongoTemplate.findOne(query, Event.class);


        if(event != null){
            String approval = event.getApproval();
            event.setEventName(eventRequest.eventName());
            event.setOrganizerId(eventRequest.organizerId());
            event.setRoomId(eventRequest.roomId());
            event.setBookingId(eventRequest.bookingId());
            event.setEventType(eventRequest.eventType());
            event.setExpectedAttendees(eventRequest.expectedAttendees());
            event.setStartTime(eventRequest.startTime());
            event.setEndTime(eventRequest.endTime());
            event.setEmail(eventRequest.email());
            event.setOrganizerRole(eventRequest.organizerRole());
            event.setOrganizerName(eventRequest.organizerName());
            event.setApproval(approval);
            return eventRepository.save(event).getId();
        }

        return id;

    }

    @Override
    public String updateApproval(String id, String approval) {
        Event eventUpdate = eventRepository.findById(id).orElse(null);

        if(eventUpdate != null){
            eventUpdate.setApproval(approval);
            return eventRepository.save(eventUpdate).getId() + " has been " + approval;
        }

        return id;
    }

    @Override
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }
}
