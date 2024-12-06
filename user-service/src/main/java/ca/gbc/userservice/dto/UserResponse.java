package ca.gbc.userservice.dto;

import ca.gbc.userservice.model.UserType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.ToString;

//@ToString
//@JsonSerialize
public record UserResponse(
        Long id,
        String name,
        String email,
        UserType userType,
        String role
) {

}
