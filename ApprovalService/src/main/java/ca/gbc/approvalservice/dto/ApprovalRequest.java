package ca.gbc.approvalservice.dto;

public record ApprovalRequest(
        String eventId,
        Long userId,
        String status
) {
}
