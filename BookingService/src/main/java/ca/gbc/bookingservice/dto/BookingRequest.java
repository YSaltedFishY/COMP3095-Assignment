package ca.gbc.bookingservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

public record BookingRequest(
        Long userId,
        Long roomId,
        Date startTime,
        Date endTime,
        String purpose
) {
}
