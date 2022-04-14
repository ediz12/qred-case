package se.qred.task.core.model;

import se.qred.task.api.response.ApplicationFullResponse;
import se.qred.task.db.dto.Application;

public class ApplicationPair {

    private final Application application;
    private final ApplicationFullResponse applicationResponse;

    public ApplicationPair(Application application, ApplicationFullResponse applicationResponse) {
        this.application = application;
        this.applicationResponse = applicationResponse;
    }

    public Application getApplication() {
        return application;
    }

    public ApplicationFullResponse getApplicationResponse() {
        return applicationResponse;
    }
}
