package ca.gbc.roomservice.dto;

import java.math.BigDecimal;

public record RoomResponse(
        Long id,


        Boolean availability,
        String room_name,
        String features,
        BigDecimal price,
        Integer capacity
) {
}
