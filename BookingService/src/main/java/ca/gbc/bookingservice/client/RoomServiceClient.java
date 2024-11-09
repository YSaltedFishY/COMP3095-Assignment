package ca.gbc.bookingservice.client;

import ca.gbc.roomservice.dto.RoomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "BookingService", url = "http://localhost:8082")
public interface RoomServiceClient {
    @GetMapping("/api/room/available}")
    List<RoomResponse> getBookingDetails();
}