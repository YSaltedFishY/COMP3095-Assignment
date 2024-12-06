package ca.gbc.eventservice.dto;

public record EventResponse(
        String id,

        String eventName,
        Long organizerId,//UserId from user-service
        Long roomId,
        String bookingId,
        String eventType,
        int expectedAttendees
) {
}
