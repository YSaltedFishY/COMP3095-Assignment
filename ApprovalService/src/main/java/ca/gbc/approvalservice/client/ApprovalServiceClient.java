package ca.gbc.approvalservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "approval-service",url = "http://localhost:8085")
public interface ApprovalServiceClient {




}
