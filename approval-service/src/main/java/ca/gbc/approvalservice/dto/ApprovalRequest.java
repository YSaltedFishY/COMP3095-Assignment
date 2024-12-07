package ca.gbc.approvalservice.dto;

import java.time.LocalDateTime;

public record ApprovalRequest(
        Long Id,
        String eventId,
        Long userId,
        String userRole,
        String status,
        LocalDateTime time
) {
}
