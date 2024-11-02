package ca.gbc.userservice.dto;

import ca.gbc.userservice.model.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public record UserRequest(
        Long id,
        String name,
        String email,
        UserType userType,
        String role){
}
