package se.qred.task.core.mapper.response;

import se.qred.task.api.response.ApplicationApplyResponse;
import se.qred.task.api.response.ApplicationFullResponse;
import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.db.dto.Application;

public class ApplicationResponseMapper {

    public ApplicationApplyResponse map(Application application) {
        return new ApplicationApplyResponse.Builder()
                .id(application.getId())
                .amount(application.getAmount())
                .email(application.getEmail())
                .phoneNumber(application.getPhoneNumber())
                .organization(new OrganizationResponse.Builder()
                        .id(application.getOrganization().getId())
                        .build())
                .build();
    }

    public ApplicationFullResponse mapFull(Application application) {
        return new ApplicationFullResponse.Builder()
                .id(application.getId())
                .amount(application.getAmount())
                .email(application.getEmail())
                .phoneNumber(application.getPhoneNumber())
                .status(application.getStatus())
                .appliedDate(application.getAppliedDate())
                .organization(new OrganizationResponse.Builder()
                        .id(application.getOrganization().getId())
                        .build())
                .build();
    }
}
