package ca.gbc.bookingservice.client;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface UserClient {
    //Circuit breaker
    Logger log  = LoggerFactory.getLogger(UserClient.class);

    @GetExchange("/api/user/info/{userId}")
    @CircuitBreaker(name = "user", fallbackMethod = "fallbackMethod")
    @Retry(name = "user")
    boolean getUserInfo(@PathVariable ("userId") Long userId);

    default Boolean fallbackMethod(Throwable throwable) {
        log.info("CB message: " + throwable.getMessage());
        return false;
    }

}
