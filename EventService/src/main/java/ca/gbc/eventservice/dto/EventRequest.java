package ca.gbc.eventservice.dto;

public record EventRequest(String id,
        String eventName,
        String organizerId,
        String eventType,
        int expectedAttendees,
        String roomId) {

}
