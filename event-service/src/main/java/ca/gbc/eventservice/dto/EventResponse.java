package ca.gbc.eventservice.dto;

import java.time.LocalDateTime;

public record EventResponse(
        String id,

        String bookingId,
        Long roomId,

        Long organizerId,
        String organizerName,
        String organizerRole,
        String eventName,
        String eventType,
        int expectedAttendees,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String email,
        String approval

) {
}
