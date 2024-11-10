package ca.gbc.bookingservice.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record BookingResponse(
        String bookingId,
        Long UserId,
        Long roomId,
        Date startTime,
        Date endTime,
        String purpose
) {
}
