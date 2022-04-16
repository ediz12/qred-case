package se.qred.task.client;

import org.glassfish.jersey.client.JerseyWebTarget;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.client.MockAllabolagOrganizationResponse;
import se.qred.task.client.model.AllabolagOrganizationDetailResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AllabolagClientTest extends BaseMockitoTest {

    @Mock
    private Client client;

    private AllabolagClient allabolagClient;

    @Before
    public void setUp() throws Exception {
        allabolagClient = new AllabolagClient(client);
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void getOrganizationDetails() {
        // Given
        final String ORGANIZATION_NUMBER_PLACEHOLDER = "{organizationNumber}";
        final String BASE_URL = "http://localhost:5000";
        final String ORGANIZATION_DETAILS_URL = "organizations/" + ORGANIZATION_NUMBER_PLACEHOLDER;
        final String organizationNumber = "123456-7890";
        final AllabolagOrganizationDetailResponse expectedOrganization = MockAllabolagOrganizationResponse.simpleOrganization();

        // When
        // when(client.target(BASE_URL)).thenReturn(new JerseyWebTarget(BASE_URL, ))
        // when(client.target(BASE_URL)
        //        .path(ORGANIZATION_DETAILS_URL.replace(ORGANIZATION_NUMBER_PLACEHOLDER, organizationNumber))
        //        .request(MediaType.APPLICATION_JSON)
        //        .get(AllabolagOrganizationDetailResponse.class))
        //        .thenReturn(expectedOrganization);

        // Then
        //final AllabolagOrganizationDetailResponse organization = allabolagClient.getOrganizationDetails(organizationNumber);
        // verify(client).target(BASE_URL)
        //        .path(ORGANIZATION_DETAILS_URL.replace(ORGANIZATION_NUMBER_PLACEHOLDER, organizationNumber))
        //        .request(MediaType.APPLICATION_JSON)
        //        .get(AllabolagOrganizationDetailResponse.class);
        // verifyNoMoreInteractions(client);
    }
}