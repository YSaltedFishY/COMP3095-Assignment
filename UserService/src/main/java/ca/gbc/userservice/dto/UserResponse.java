package ca.gbc.userservice.dto;

import ca.gbc.userservice.model.UserType;

import java.math.BigDecimal;

public record UserResponse(
        Long id,
        String name,
        String email,
        UserType userType,
        String role
) {
}
