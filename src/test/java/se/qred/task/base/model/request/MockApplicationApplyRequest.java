package se.qred.task.base.model.request;

import se.qred.task.api.request.ApplicationApplyRequest;

public class MockApplicationApplyRequest {

    private MockApplicationApplyRequest() {

    }

    public static ApplicationApplyRequest simpleApplyRequest() {
        return new ApplicationApplyRequest.Builder()
                .amount(10000)
                .email("test@test.com")
                .phoneNumber("+1234567890")
                .organizationNumber("1234567890")
                .build();
    }
}
