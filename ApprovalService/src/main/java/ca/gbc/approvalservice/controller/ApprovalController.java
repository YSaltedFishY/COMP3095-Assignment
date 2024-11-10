package ca.gbc.approvalservice.controller;

import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.dto.ApprovalResponse;
import ca.gbc.approvalservice.dto.EventResponse;
import ca.gbc.approvalservice.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approval")
@RequiredArgsConstructor
public class ApprovalController {

    private final ApprovalService approvalService;

    @PostMapping("/approve")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApprovalResponse> approveEvent(@RequestBody ApprovalRequest request){
        ApprovalResponse response = approvalService.approveEvent(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ApprovalResponse> getAllApprovals(){
        return approvalService.getAllApproval();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApprovalResponse> getApproval(@PathVariable Long id){
        return approvalService.getApprovalStatus(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApproval(@PathVariable("id") Long id,
                                            @RequestBody ApprovalRequest approvalRequest){
        Long updateApprovalId = approvalService.updateApproval(id,approvalRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/approvals/" + updateApprovalId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApprovalResponse> deleteApproval(@PathVariable ("id") Long id){
        approvalService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
