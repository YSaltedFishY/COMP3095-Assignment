package ca.gbc.bookingservice.client;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface RoomClient {
    @GetExchange("/api/room/{roomId}")
    Boolean getRoomAvailability(@PathVariable ("roomId") Long roomId);

    @PutExchange("/api/room/available/{roomId}")
    Long updateAvailability(@PathVariable("roomId") Long roomId, @RequestBody Boolean bool);

}
