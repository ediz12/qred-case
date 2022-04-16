package se.qred.task.base.model.response;

import se.qred.task.api.response.ApplicationFullResponse;
import se.qred.task.core.model.enums.ApplicationStatus;

public class MockApplicationFullResponse {

    private MockApplicationFullResponse() {

    }

    public static ApplicationFullResponse simpleFullResponse() {
        return new ApplicationFullResponse.Builder()
                .id(1L)
                .amount(10000)
                .email("test@test.com")
                .phoneNumber("+1234567890")
                .status(ApplicationStatus.PENDING)
                .organization(MockOrganizationResponse.simpleOrganization())
                .build();
    }

    public static ApplicationFullResponse simpleFullResponseWithOffer() {
        return new ApplicationFullResponse.Builder()
                .id(1L)
                .amount(10000)
                .email("test@test.com")
                .phoneNumber("+1234567890")
                .status(ApplicationStatus.SIGNED)
                .organization(MockOrganizationResponse.simpleOrganization())
                .offer(MockOfferFullResponse.simpleFullResponse())
                .build();
    }
}
