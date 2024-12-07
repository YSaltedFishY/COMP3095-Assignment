package ca.gbc.approvalservice.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface EventClient {
    @PutExchange("/api/event/approve/{id}/{approval}")
    String updateApproval(@PathVariable("id") String id,
                          @PathVariable("approval") String approval);
}
