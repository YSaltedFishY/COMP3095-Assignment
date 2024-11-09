package ca.gbc.approvalservice.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class UserServiceClientStub {

    public static void stubUserApprovalCheck(Long userId) {
        stubFor(get(urlEqualTo("/api/user/approve/" + userId))
                .willReturn(aResponse()
                        .withStatus(200) // The status code to expect from user service
                        .withHeader("Content-Type", "application/json")
                        .withBody("True"))
        );
    }


}
