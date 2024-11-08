package ca.gbc.bookingservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

public record BookingRequest(
        Long bookingId,
        Long UserId,
        Long roomId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String purpose
) {
}
