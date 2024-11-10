package ca.gbc.approvalservice.client;

import ca.gbc.approvalservice.dto.EventResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "event-service", url="event.service.url")
public interface EventServiceClient {

    @GetMapping("/api/event/{id}")
    EventResponse getEventById(@PathVariable String id);


    @GetMapping("/api/event/exists/{id}")
    boolean checkEventExists(@PathVariable String id);


}
