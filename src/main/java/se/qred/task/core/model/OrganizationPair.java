package se.qred.task.core.model;

import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.db.dto.Organization;

public class OrganizationPair {

    private final Organization organization;
    private final OrganizationResponse organizationResponse;

    public OrganizationPair(Organization organization, OrganizationResponse organizationResponse) {
        this.organization = organization;
        this.organizationResponse = organizationResponse;
    }

    public Organization getOrganization() {
        return organization;
    }

    public OrganizationResponse getOrganizationResponse() {
        return organizationResponse;
    }
}
