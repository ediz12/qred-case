package se.qred.task.base.model.db;

import org.joda.time.DateTime;
import se.qred.task.core.model.enums.ApplicationStatus;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.Offer;

public class MockApplication {

    private MockApplication() {

    }

    public static Application simpleCreatedApplication() {
        return new Application.Builder()
                .userId("1")
                .amount(10000)
                .email("test@test.com")
                .phoneNumber("+1234567890")
                .organization(MockOrganization.getSimpleOrganization())
                .status(ApplicationStatus.PROCESSED)
                .appliedDate(new DateTime())
                .build();
    }

    public static Application simpleProcessedApplication() {
        return new Application.Builder()
                .id(1L)
                .userId("1")
                .amount(10000)
                .email("test@test.com")
                .phoneNumber("+1234567890")
                .organization(MockOrganization.getSimpleOrganization())
                .status(ApplicationStatus.PROCESSED)
                .appliedDate(new DateTime())
                .build();
    }

    public static Application simplePendingApplicationWithOffer() {
        return new Application.Builder()
                .id(1L)
                .userId("1")
                .amount(10000)
                .email("test@test.com")
                .phoneNumber("+1234567890")
                .organization(MockOrganization.getSimpleOrganization())
                .offer(new Offer.Builder()
                        .id(1L)
                        .build())
                .status(ApplicationStatus.PROCESSED)
                .appliedDate(new DateTime())
                .build();
    }
}
