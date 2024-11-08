package ca.gbc.bookingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8084")
public interface UserServiceClient {

    @GetMapping("/users/{userId}/role")
    String getUserRole(@PathVariable("userId") Long userId);
}
