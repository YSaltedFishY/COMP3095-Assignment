package ca.gbc.approvalservice.service;

import ca.gbc.approvalservice.client.EventServiceClient;
import ca.gbc.approvalservice.client.UserServiceClient;
import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.dto.ApprovalResponse;
import ca.gbc.approvalservice.dto.EventResponse;
import ca.gbc.approvalservice.model.Approval;
import ca.gbc.approvalservice.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@EnableFeignClients(basePackages = "ca.gbc.approvalservice.client")
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final UserServiceClient userServiceClient;
    private final EventServiceClient eventServiceClient;

    @Override
    public ApprovalResponse approveEvent(ApprovalRequest approvalRequest) {

        var isUserStaff = userServiceClient.checkIfUserStaff(approvalRequest.userId());
        log.debug(isUserStaff.toString());

        if(isUserStaff) {
            Approval approval = new Approval();
            approval.setEventId(approvalRequest.eventId());
            approval.setUserId(approvalRequest.userId());
            approval.setStatus("APPROVED");
            approval.setTime(LocalDateTime.now());
            Approval newApproval = approvalRepository.save(approval);
            return new ApprovalResponse(newApproval.getId(), newApproval.getEventId(),
                    newApproval.getUserId(), newApproval.getStatus(), newApproval.getTime());
        }else{
            return rejectEvent(approvalRequest);
        }
    }

    @Override
    public ApprovalResponse rejectEvent(ApprovalRequest approvalRequest) {
        Approval approval = new Approval();
        approval.setEventId(approvalRequest.eventId());
        approval.setUserId(approvalRequest.userId());
        approval.setStatus("REJECTED");
        approval.setTime(LocalDateTime.now());
        Approval newApproval = approvalRepository.save(approval);
        return new ApprovalResponse(newApproval.getId(), newApproval.getEventId(),
                newApproval.getUserId(), newApproval.getStatus(), newApproval.getTime());
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
            return approvalRepository.save(updateApproval).getId();
        }

        return id;
    }

    public EventResponse fetchEventDetails(String eventId){
        return eventServiceClient.getEventById(eventId);
    }

    @Override
    public void deleteUser(Long id) {
        approvalRepository.deleteById(id);
    }

    private ApprovalResponse mapToApprovalResponse(Approval approval){
        return new ApprovalResponse(approval.getId(), approval.getEventId(),
                approval.getUserId(), approval.getStatus(), approval.getTime());
    }
}
