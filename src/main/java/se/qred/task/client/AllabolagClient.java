package se.qred.task.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.qred.task.client.model.AllabolagOrganizationDetailResponse;
import se.qred.task.core.model.exceptions.AllabolagUnreachableException;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

public class AllabolagClient {

    private static final Logger logger  = LoggerFactory.getLogger(AllabolagClient.class);
    private static final String ORGANIZATION_NUMBER_PLACEHOLDER = "{organizationNumber}";
    private static final String BASE_URL = "http://localhost:5000";
    private static final String ORGANIZATION_DETAILS_URL = "organizations/" + ORGANIZATION_NUMBER_PLACEHOLDER;

    private final Client client;

    public AllabolagClient(Client client) {
        this.client = client;
    }

    public AllabolagOrganizationDetailResponse getOrganizationDetails(String organizationNumber) throws AllabolagUnreachableException {
        try {
            return client
                    .target(BASE_URL)
                    .path(ORGANIZATION_DETAILS_URL.replace(ORGANIZATION_NUMBER_PLACEHOLDER, organizationNumber))
                    .request(MediaType.APPLICATION_JSON)
                .get(AllabolagOrganizationDetailResponse.class);
        } catch (ProcessingException e) {
            logger.debug(String.format("Allabolag get details URL format: %s/%s", BASE_URL, ORGANIZATION_DETAILS_URL.replace(ORGANIZATION_NUMBER_PLACEHOLDER, organizationNumber)));
            logger.error(String.format("Error connecting to Allabolag client: %s", e));
            throw new AllabolagUnreachableException("Allabolag server is not up and running!");
        }
    }
}
