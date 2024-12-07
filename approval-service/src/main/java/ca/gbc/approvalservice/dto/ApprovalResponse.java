package ca.gbc.approvalservice.dto;

import java.time.LocalDateTime;

public record ApprovalResponse(
        Long Id,
        String eventId,
        Long userId,
        String userRole,
        String status,
        LocalDateTime time
) {
}
