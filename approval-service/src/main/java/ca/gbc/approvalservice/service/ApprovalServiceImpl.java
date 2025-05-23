package ca.gbc.approvalservice.service;

import ca.gbc.approvalservice.client.EventClient;
import ca.gbc.approvalservice.client.UserClient;
import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.dto.ApprovalResponse;
import ca.gbc.approvalservice.event.EventMadeEvent;
import ca.gbc.approvalservice.model.Approval;
import ca.gbc.approvalservice.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final UserClient userClient;
    private final EventClient eventClient;

    @KafkaListener(topics = "event-made")
    public void listen(EventMadeEvent eventMadeEvent){
        Approval approval = Approval.builder()
                .eventId(eventMadeEvent.getEventId())
                .userId(eventMadeEvent.getOrganizerId())
                .userRole(eventMadeEvent.getOrganizerRole())
                .status("PENDING")
                .time(LocalDateTime.now())
                .build();
        approvalRepository.save(approval);
    }



    @Override
    public ApprovalResponse approveEvent(ApprovalRequest approvalRequest) {

        var isUserStaff = userClient.checkIfUserStaff(approvalRequest.userId());
        log.debug(isUserStaff.toString());

        if(isUserStaff) {
            Approval approval = new Approval();
            approval.setEventId(approvalRequest.eventId());
            approval.setUserId(approvalRequest.userId());
            approval.setStatus("APPROVED");
            approval.setUserRole(approvalRequest.userRole());
            approval.setTime(LocalDateTime.now());
            Approval newApproval = approvalRepository.save(approval);
            return new ApprovalResponse(newApproval.getId(), newApproval.getEventId(),
                    newApproval.getUserId(), newApproval.getUserRole(),
                    newApproval.getStatus(), newApproval.getTime());
        }else{
            return rejectEvent(approvalRequest);
        }
    }

    @Override
    public ApprovalResponse rejectEvent(ApprovalRequest approvalRequest) {
        Approval approval = new Approval();
        approval.setEventId(approvalRequest.eventId());
        approval.setUserId(approvalRequest.userId());
        approval.setUserRole(approvalRequest.userRole());
        approval.setStatus("REJECTED");
        approval.setTime(LocalDateTime.now());
        Approval newApproval = approvalRepository.save(approval);
        return new ApprovalResponse(newApproval.getId(), newApproval.getEventId(),
                newApproval.getUserId(), newApproval.getUserRole(),
                newApproval.getStatus(), newApproval.getTime());
    }

    @Override
    public List<ApprovalResponse> getAllApproval() {
        List<Approval> approvals = approvalRepository.findAll();

        return approvals.stream().map(this::mapToApprovalResponse).toList();
    }

    @Override
    public Optional<ApprovalResponse> getApprovalStatus(Long id){
        return approvalRepository.findById(id).map(this::mapToApprovalResponse);
    }

    @Override
    public Long updateApproval(Long id, ApprovalRequest approvalRequest) {
        Approval updateApproval = approvalRepository.findById(id).orElse(null);

        if(updateApproval != null){
            updateApproval.setStatus(approvalRequest.status());
            updateApproval.setTime(LocalDateTime.now());

            eventClient.updateApproval(updateApproval.getEventId(),approvalRequest.status());
            return approvalRepository.save(updateApproval).getId();
        }

        return id;
    }

    @Override
    public void deleteUser(Long id) {
        approvalRepository.deleteById(id);
    }

    private ApprovalResponse mapToApprovalResponse(Approval approval){
        return new ApprovalResponse(approval.getId(), approval.getEventId(),
                approval.getUserId(), approval.getUserRole(),
                approval.getStatus(), approval.getTime());
    }
}