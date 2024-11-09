package ca.gbc.eventservice.client;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "BookingService", url = "http://localhost:8080")
public interface BookingServiceClient {
    @GetMapping("/api/booking")
    ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest);

    @PutMapping("/api/booking/{bookingId}")
    ResponseEntity<BookingResponse> updateBooking(@PathVariable("bookingId") String bookingId,@RequestBody BookingRequest bookingRequest);

}