package ca.gbc.eventservice.dto;

import java.util.Date;

public record EventRequest(
        String id,
        String eventName,
        String organizerId,
        String eventType,
        int expectedAttendees,
        Long roomId,
        // for booking

        String bookingId,
        Long userId,
        Date startTime,
        Date endTime,
        String purpose
) {


}
