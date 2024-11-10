package ca.gbc.approvalservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url="${user.service.url}")
public interface UserServiceClient {

    @GetMapping("/api/user/approve/{userId}")
    Boolean checkIfUserStaff(@PathVariable("userId") Long userId);

}
