package se.qred.task.core.mapper.request;

import org.joda.time.DateTime;
import se.qred.task.api.request.ApplicationApplyRequest;
import se.qred.task.db.dto.Application;
import se.qred.task.core.model.enums.ApplicationStatus;
import se.qred.task.db.dto.Organization;

public class ApplicationRequestMapper {

    public Application map(ApplicationApplyRequest request, Organization organization, String userId) {
        return new Application.Builder()
                .userId(userId)
                .organization(organization)
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .amount(request.getAmount())
                .status(ApplicationStatus.PROCESSED)
                .appliedDate(new DateTime())
                .build();
    }
}
