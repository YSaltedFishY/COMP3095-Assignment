package ca.gbc.approvalservice.dto;

public record ApprovalRequest(
        Long eventId,
        Long userId,
        String status
) {
}
