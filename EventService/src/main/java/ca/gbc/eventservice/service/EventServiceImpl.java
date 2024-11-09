package ca.gbc.eventservice.service;

import ca.gbc.approvalservice.client.UserServiceClient;
import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    //private final EventServiceClient userServiceClient;

    @Override
    public EventResponse createEvent(EventRequest eventRequest) {
        log.debug("Creating a new event");

//        Boolean isStaff = userServiceClient.checkIfUserStaff(Long.parseLong(eventRequest.organizerId()));
//        if (!isStaff) {
//            throw new IllegalArgumentException("Only staff members can create events.");
//        }

        Event event = Event.builder()
                .eventName(eventRequest.eventName())
                .organizerId(eventRequest.organizerId())
                .eventType(eventRequest.eventType())
                .expectedAttendees(eventRequest.expectedAttendees())
                .roomId(eventRequest.roomId())
                .build();

        // Save the event to the repository
        Event savedEvent = eventRepository.save(event);

        // Convert the saved Event to EventResponse DTO
        return new EventResponse(
                savedEvent.getId(),
                savedEvent.getEventName(),
                savedEvent.getOrganizerId(),
                savedEvent.getEventType(),
                savedEvent.getExpectedAttendees(),
                savedEvent.getRoomId()
        );
    }

    @Override
    public List<EventResponse> getAllEvents() {
        log.debug("Returning a list of all events");

        // Retrieve all events and map them to EventResponse DTOs
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::mapToEventResponse).toList();
    }

    @Override
    public List<EventResponse> getUserEvents(String organizerId) {
        log.debug("Returning events for organizer with ID: {}", organizerId);

        // Retrieve events for the specified organizer and map them to EventResponse DTOs
        List<Event> userEvents = eventRepository.findByOrganizerId(organizerId);
        return userEvents.stream()
                .map(this::mapToEventResponse).toList();
    }

    @Override
    public String updateEvent(String eventId, EventRequest eventRequest) {
        log.debug("Updating event with ID: {}", eventId);

        // Find the existing event
        Event event = eventRepository.findById(eventId).orElse(null);

        if (event != null) {
            event = Event.builder()
                    .id(event.getId())
                    .eventName(eventRequest.eventName())
                    .organizerId(eventRequest.organizerId())
                    .eventType(eventRequest.eventType())
                    .expectedAttendees(eventRequest.expectedAttendees())
                    .roomId(eventRequest.roomId())
                    .build();

            // Save the updated event
            Event updatedEvent = eventRepository.save(event);
            log.info("Event updated successfully");
            return updatedEvent.getId();
        }

        log.warn("Event with ID: {} not found", eventId);
        return null;
    }

    @Override
    public EventResponse deleteEvent(String eventId) {
        log.debug("Deleting event with ID: {}", eventId);

        // Find and delete the event
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            eventRepository.delete(event);
            log.info("Event deleted successfully");
            return mapToEventResponse(event);
        }

        log.warn("Event with ID: {} not found", eventId);
        return null;
    }

    private EventResponse mapToEventResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getEventName(),
                event.getOrganizerId(),
                event.getEventType(),
                event.getExpectedAttendees(),
                event.getRoomId()
        );
    }
}