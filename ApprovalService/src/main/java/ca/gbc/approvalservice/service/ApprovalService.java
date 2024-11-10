package ca.gbc.approvalservice.service;

import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.dto.ApprovalResponse;
import ca.gbc.approvalservice.dto.EventResponse;

import java.util.List;
import java.util.Optional;

public interface ApprovalService {
    ApprovalResponse approveEvent(ApprovalRequest approvalRequest);

    ApprovalResponse rejectEvent(ApprovalRequest approvalRequest);

    List<ApprovalResponse> getAllApproval();

    Long updateApproval(Long id, ApprovalRequest approvalRequest);

    void deleteUser(Long id);

    EventResponse fetchEventDetails(String eventId);

    Optional<ApprovalResponse> getApprovalStatus(Long id);
}
