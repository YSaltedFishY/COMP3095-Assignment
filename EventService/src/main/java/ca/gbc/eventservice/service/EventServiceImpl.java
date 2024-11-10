package ca.gbc.eventservice.service;

import ca.gbc.approvalservice.client.UserServiceClient;
import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.model.Booking;
import ca.gbc.eventservice.client.BookingServiceClient;
import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository;
    private final BookingServiceClient bookingServiceClient;

    @Override
    public EventResponse createEvent(EventRequest eventRequest) {
        log.debug("Creating a new event");


        try {
            Booking booking = Booking.builder()
                    .userId(eventRequest.userId())
                    .startTime(eventRequest.startTime())
                    .endTime(eventRequest.endTime())
                    .purpose(eventRequest.purpose())
                    .roomId(eventRequest.roomId()).build();

            Event event = Event.builder()
                    .eventName(eventRequest.eventName())
                    .organizerId(eventRequest.organizerId())
                    .eventType(eventRequest.eventType())
                    .expectedAttendees(eventRequest.expectedAttendees())
                    .roomId(eventRequest.roomId())
                    .build();
            Event savedEvent = eventRepository.save(event);
            this.createBooking(booking);
            // Convert the saved Event to EventResponse DTO
            return new EventResponse(
                    savedEvent.getId(),
                    savedEvent.getEventName(),
                    savedEvent.getOrganizerId(),
                    savedEvent.getEventType(),
                    savedEvent.getExpectedAttendees(),
                    savedEvent.getBookingId()
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Room cannot be booked during this time period, no event was created");

        }


        // Save the event to the repository


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
    public String updateEvent(String eventId, EventRequest eventRequest) {
        log.debug("Updating event with ID: {}", eventId);

        // Find the existing event
        Event event = eventRepository.findById(eventId).orElse(null);

        if (event != null) {
            try {
                Booking booking = Booking.builder()
                        .userId(eventRequest.userId())
                        .startTime(eventRequest.startTime())
                        .endTime(eventRequest.endTime())
                        .purpose(eventRequest.purpose())
                        .roomId(eventRequest.roomId()).build();


                var savedbooking = bookingServiceClient.updateBooking(booking.getBookingId(), new BookingRequest(
                       booking.getBookingId(), booking.getUserId(), booking.getRoomId(), booking.getStartTime(), booking.getEndTime(), booking.getPurpose()
                ));
                event = Event.builder()
                        .id(event.getId())
                        .eventName(eventRequest.eventName())
                        .organizerId(eventRequest.organizerId())
                        .eventType(eventRequest.eventType())
                        .expectedAttendees(eventRequest.expectedAttendees())
                        .roomId(booking.getRoomId())
                        .bookingId(booking.getBookingId())
                        .build();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Room cannot be booked during this time period, no event was created");

            }


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

    @Override
    public EventResponse getEventWithBookingId(String bookingId) {
        Event event = eventRepository.findByBookingId(bookingId);
        return mapToEventResponse(event);
    }

    private EventResponse mapToEventResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getEventName(),
                event.getOrganizerId(),
                event.getEventType(),
                event.getExpectedAttendees(),
                event.getBookingId()
        );
    }

    private ResponseEntity<BookingResponse> createBooking(Booking booking) {
        BookingRequest bookingRequest = new BookingRequest(
               booking.getBookingId(), booking.getUserId(), booking.getRoomId(), booking.getStartTime(), booking.getEndTime(), booking.getPurpose()

        );
        return bookingServiceClient.createBooking(bookingRequest);

    }
}