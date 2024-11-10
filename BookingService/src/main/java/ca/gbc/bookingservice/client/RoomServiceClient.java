package ca.gbc.bookingservice.client;

import ca.gbc.roomservice.dto.RoomResponse;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "BookingService", url = "http://localhost:8082")
public interface RoomServiceClient {

    @GetMapping("/api/room/{roomId}")
    Boolean checkRoomAvailability(@PathVariable("roomId") Long roomId);

    @PutMapping("/api/room/available/{roomId}")
    ResponseEntity<RoomResponse> updateAvailability(@PathVariable("roomId") Long roomId, @RequestBody Boolean bool);



}