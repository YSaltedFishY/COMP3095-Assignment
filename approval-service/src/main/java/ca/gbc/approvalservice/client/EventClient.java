package ca.gbc.approvalservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface EventClient {
    Logger log = LoggerFactory.getLogger(EventClient.class);
    @PutExchange("/api/event/approve/{id}/{approval}")
    @CircuitBreaker(name = "event", fallbackMethod = "fallbackMethod")
    @Retry(name = "event")
    String updateApproval(@PathVariable("id") String id,
                          @PathVariable("approval") String approval);

    default Boolean fallbackMethod(Throwable throwable) {
        log.info("CB message: " + throwable.getMessage());
        return false;
    }
}
