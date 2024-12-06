package ca.gbc.eventservice.service;


import ca.gbc.eventservice.client.BookingClient;
import ca.gbc.eventservice.client.UserClient;
import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.repository.EventRepository;
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
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final MongoTemplate mongoTemplate;
    private final BookingClient bookingClient;
    private final UserClient userClient;

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
                    .build();
            eventRepository.save(event);

            return new EventResponse(event.getId(), event.getEventName(), event.getOrganizerId(), event.getRoomId(),
                    event.getBookingId(), event.getEventType(), event.getExpectedAttendees());
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
        return new EventResponse(event.getId(),event.getEventName(), event.getOrganizerId(),
                event.getRoomId(),event.getBookingId(),event.getEventType(),event.getExpectedAttendees());
    }

    @Override
    public String updateEvent(String id, EventRequest eventRequest) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Event event = mongoTemplate.findOne(query, Event.class);

        if(event != null){
            event.setEventName(eventRequest.eventName());
            event.setOrganizerId(eventRequest.organizerId());
            event.setRoomId(eventRequest.roomId());
            event.setBookingId(eventRequest.bookingId());
            event.setEventType(eventRequest.eventType());
            event.setExpectedAttendees(eventRequest.expectedAttendees());

            return eventRepository.save(event).getId();
        }

        return id;

    }

    @Override
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }
}
