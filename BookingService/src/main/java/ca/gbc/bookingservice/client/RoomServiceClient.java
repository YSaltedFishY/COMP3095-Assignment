package ca.gbc.bookingservice.client;

import ca.gbc.roomservice.dto.RoomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "BookingService", url = "http://localhost:8082")
public interface RoomServiceClient {
    @GetMapping("/api/room/{roomId}")
    List<RoomResponse> checkRoomAvailability(@PathVariable("roomId") Long roomId);


}