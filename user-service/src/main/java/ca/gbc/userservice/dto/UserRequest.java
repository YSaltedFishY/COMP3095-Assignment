package ca.gbc.userservice.dto;

import ca.gbc.userservice.model.UserType;

public record UserRequest(
        Long id,
        String name,
        String email,
        UserType user_type,
        String role
) {
}
