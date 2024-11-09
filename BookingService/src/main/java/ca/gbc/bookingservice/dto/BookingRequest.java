package ca.gbc.bookingservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

public record BookingRequest(
        String bookingId,
        Long userId,
        Long roomId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String purpose
) {
}
