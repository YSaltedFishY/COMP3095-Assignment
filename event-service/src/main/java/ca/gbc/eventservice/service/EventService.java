package ca.gbc.eventservice.service;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;

import java.util.List;

public interface EventService {
    EventResponse createEvent(EventRequest eventRequest);
    String updateApproval(String id, String approval);

    List<EventResponse> getAllEvents();

    String updateEvent(String EventId, EventRequest eventRequest);

    void deleteEvent(String eventId);
}
