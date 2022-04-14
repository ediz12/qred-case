package se.qred.task.core.mapper.request;

import se.qred.task.client.model.AllabolagOrganizationDetailResponse;
import se.qred.task.db.dto.Organization;

public class OrganizationRequestMapper {
    
    public Organization map(AllabolagOrganizationDetailResponse allabolagOrganizationDetailResponse) {
        return new Organization.Builder()
                .organizationName(allabolagOrganizationDetailResponse.getOrganizationName())
                .organizationNumber(allabolagOrganizationDetailResponse.getOrganizationNumber().replace("-", ""))
                .organizationType(allabolagOrganizationDetailResponse.getOrganizationType())
                .build();
    }
}
