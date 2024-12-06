package ca.gbc.eventservice.client;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface UserClient {

    @GetExchange("/api/user/approve/{userId}")
    Boolean checkIfUserStaff( @PathVariable("userId") Long userId);


}
