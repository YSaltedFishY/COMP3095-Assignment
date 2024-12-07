package ca.gbc.bookingservice.client;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface RoomClient {
    //Circuit breaker
    Logger log  = LoggerFactory.getLogger(RoomClient.class);

    @GetExchange("/api/room/{roomId}")
    @CircuitBreaker(name = "room", fallbackMethod = "fallbackMethod")
    @Retry(name = "room")

    Boolean getRoomAvailability(@PathVariable ("roomId") Long roomId);
    @PutExchange("/api/room/available/{roomId}")
    Long updateAvailability(@PathVariable("roomId") Long roomId, @RequestBody Boolean bool);

    //Circuit breaker
    default Boolean fallbackMethod(String code, Integer quantity, Throwable throwable) {
        log.info("Cannot get room {}, failure reason: {}", code, throwable.getMessage());
        return false;
    }

}
