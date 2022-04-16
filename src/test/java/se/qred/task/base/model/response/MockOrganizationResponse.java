package se.qred.task.base.model.response;

import se.qred.task.api.response.OrganizationResponse;

public class MockOrganizationResponse {

    private MockOrganizationResponse() {

    }

    public static OrganizationResponse simpleOrganization() {
        return new OrganizationResponse.Builder()
                .id(1L)
                .organizationName("Best Company AB")
                .organizationNumber("123456-7890")
                .organizationType("Private Business")
                .build();
    }

    public static OrganizationResponse simpleModifiedOrganization() {
        return new OrganizationResponse.Builder()
                .id(1L)
                .organizationName("Best Company AB")
                .organizationNumber("1234567890")
                .organizationType("Private Business")
                .build();
    }
}
