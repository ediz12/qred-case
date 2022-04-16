package se.qred.task.base.model.client;

import se.qred.task.client.model.AllabolagOrganizationDetailResponse;

public class MockAllabolagOrganizationResponse {

    private MockAllabolagOrganizationResponse() {

    }

    public static AllabolagOrganizationDetailResponse simpleOrganization() {
        return new AllabolagOrganizationDetailResponse.Builder()
                .organizationName("Best Company AB")
                .organizationNumber("123456-7890")
                .organizationType("Private Business")
                .build();
    }
}
