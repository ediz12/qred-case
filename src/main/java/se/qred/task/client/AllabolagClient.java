package se.qred.task.client;

import se.qred.task.client.model.AllabolagOrganizationDetailResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

public class AllabolagClient {

    private static final String ORGANIZATION_NUMBER_PLACEHOLDER = "{organizationNumber}";
    private static final String BASE_URL = "http://localhost:5000";
    private static final String ORGANIZATION_DETAILS_URL = "organizations/" + ORGANIZATION_NUMBER_PLACEHOLDER;

    private final Client client;

    public AllabolagClient(Client client) {
        this.client = client;
    }

    public AllabolagOrganizationDetailResponse getOrganizationDetails(String organizationNumber) {
        // TODO: exception handling?
        return client
                .target(BASE_URL)
                .path(ORGANIZATION_DETAILS_URL.replace(ORGANIZATION_NUMBER_PLACEHOLDER, organizationNumber))
                .request(MediaType.APPLICATION_JSON)
                .get(AllabolagOrganizationDetailResponse.class);
    }
}
