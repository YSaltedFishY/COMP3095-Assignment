package ca.gbc.bookingservice.dto;

public record BookingResponse(
        String bookingId,
        String status,
        String message
) {
}