package ca.gbc.approvalservice.dto;

import java.time.LocalDateTime;

public record ApprovalResponse(
        Long Id,
        Long eventId,
        Long userId,
        String status,
        LocalDateTime time
) {
}
