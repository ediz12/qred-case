package se.qred.task.base.model.db;

import se.qred.task.db.dto.Organization;

public class MockOrganization {

    private MockOrganization() {

    }

    public static Organization getSimpleOrganization() {
        return new Organization.Builder()
                .id(1L)
                .organizationName("Best Company AB")
                .organizationNumber("123456-7890")
                .organizationType("Private Business")
                .build();
    }

    public static Organization getSimpleModifiedOrganization() {
        return new Organization.Builder()
                .id(1L)
                .organizationName("Best Company AB")
                .organizationNumber("1234567890")
                .organizationType("Private Business")
                .build();
    }
}
