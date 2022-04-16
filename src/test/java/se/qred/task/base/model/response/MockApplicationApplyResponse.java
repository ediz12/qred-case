package se.qred.task.base.model.response;

import se.qred.task.api.response.ApplicationApplyResponse;

public class MockApplicationApplyResponse {

    private MockApplicationApplyResponse() {

    }

    public static ApplicationApplyResponse simpleApplyResponse() {
        return new ApplicationApplyResponse.Builder()
                .id(1L)
                .amount(10000)
                .email("test@test.com")
                .phoneNumber("+1234567890")
                .organization(MockOrganizationResponse.simpleOrganization())
                .build();
    }
}
