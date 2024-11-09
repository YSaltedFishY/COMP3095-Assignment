package ca.gbc.eventservice.service;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.model.Event;

import java.util.List;

public interface EventService {

    EventResponse createEvent(EventRequest eventRequest);

    List<EventResponse> getAllEvents();

    List<EventResponse> getUserEvents(String userId);

    String updateEvent(String EventId, EventRequest eventRequest);

    EventResponse deleteEvent(String eventId);



}

