package se.qred.task.core.mapper.response;

import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.db.dto.Organization;

public class OrganizationResponseMapper {
    
    public OrganizationResponse map(Organization organization) {
        return new OrganizationResponse.Builder()
                .id(organization.getId())
                .organizationName(organization.getOrganizationName())
                .organizationNumber(organization.getOrganizationNumber())
                .organizationType(organization.getOrganizationType())
                .build();
    }
}
