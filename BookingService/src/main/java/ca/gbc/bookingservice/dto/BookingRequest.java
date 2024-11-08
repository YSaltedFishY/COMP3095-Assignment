package ca.gbc.bookingservice.dto;

import java.util.Date;

public record BookingRequest(
    String bookingId,
    String UserId,
    String roomId,
    Date startTime,
    Date endTime,
    String purpose
) {
}
