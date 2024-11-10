package ca.gbc.eventservice.service;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;

import java.util.List;

public interface EventService {

    EventResponse createEvent(EventRequest eventRequest);

    List<EventResponse> getAllEvents();

    EventResponse getEventWithBookingId(String bookingId);

    String updateEvent(String EventId, EventRequest eventRequest);

    EventResponse deleteEvent(String eventId);

}

