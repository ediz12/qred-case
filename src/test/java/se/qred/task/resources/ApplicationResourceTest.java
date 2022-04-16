package se.qred.task.resources;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import se.qred.task.api.request.ApplicationApplyRequest;
import se.qred.task.api.request.OfferCreateRequest;
import se.qred.task.api.request.OfferNegotiateManagerRequest;
import se.qred.task.api.request.OfferNegotiateUserRequest;
import se.qred.task.api.response.ApplicationApplyResponse;
import se.qred.task.api.response.ApplicationFullResponse;
import se.qred.task.api.response.OfferCreateResponse;
import se.qred.task.api.response.OfferFullResponse;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.core.facade.ApplicationFacade;
import se.qred.task.core.facade.OfferFacade;
import se.qred.task.db.dto.User;
import se.qred.task.base.model.request.MockApplicationApplyRequest;
import se.qred.task.base.model.db.MockUser;
import se.qred.task.base.model.request.MockOfferCreateRequest;
import se.qred.task.base.model.request.MockOfferNegotiateManagerRequest;
import se.qred.task.base.model.request.MockOfferNegotiateUserRequest;
import se.qred.task.base.model.response.MockApplicationApplyResponse;
import se.qred.task.base.model.response.MockApplicationFullResponse;
import se.qred.task.base.model.response.MockOfferCreateResponse;
import se.qred.task.base.model.response.MockOfferFullResponse;

import javax.ws.rs.core.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ApplicationResourceTest extends BaseMockitoTest {

    @Mock
    private ApplicationFacade applicationFacade;

    @Mock
    private OfferFacade offerFacade;

    private ApplicationResource applicationResource;

    @Before
    public void setUp() throws Exception {
        applicationResource = new ApplicationResource(applicationFacade, offerFacade);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createApplication() {
        // Given
        final User user = MockUser.simpleUser();
        final ApplicationApplyRequest applicationApplyRequest = MockApplicationApplyRequest.simpleApplyRequest();
        final ApplicationApplyResponse applicationApplyResponse = MockApplicationApplyResponse.simpleApplyResponse();
        final Response expectedResponse = Response.ok(applicationApplyResponse).build();

        // When
        when(applicationFacade.createApplication(user, applicationApplyRequest)).thenReturn(expectedResponse);

        // Then
        final Response response = applicationResource.createApplication(user, applicationApplyRequest);
        verify(applicationFacade).createApplication(user, applicationApplyRequest);
        verifyNoMoreInteractions(applicationFacade);
        verifyNoInteractions(offerFacade);

        assertEquals("Statuses are OK", expectedResponse.getStatus(), response.getStatus());

        ApplicationApplyResponse responseEntity = (ApplicationApplyResponse) response.getEntity();
        assertEquals("ID is same", applicationApplyResponse.getId(), responseEntity.getId());
        assertEquals("Amount is same", applicationApplyResponse.getAmount(), responseEntity.getAmount());
        assertEquals("Email is same", applicationApplyResponse.getEmail(), responseEntity.getEmail());
        assertEquals("Phone Number is same", applicationApplyResponse.getPhoneNumber(), responseEntity.getPhoneNumber());
        assertEquals("Organization ID is same", applicationApplyResponse.getOrganization().getId(), responseEntity.getOrganization().getId());
        // TODO add other assert equals with organization
    }

    @Test
    public void getLatestApplication() {
        // Given
        final User user = MockUser.simpleUser();
        final ApplicationFullResponse applicationFullResponse = MockApplicationFullResponse.simpleFullResponse();
        final Response expectedResponse = Response.ok(applicationFullResponse).build();

        // When
        when(applicationFacade.getLatestApplicationByUser(user)).thenReturn(expectedResponse);

        // Then
        final Response response = applicationResource.getLatestApplication(user);
        verify(applicationFacade).getLatestApplicationByUser(user);
        verifyNoMoreInteractions(applicationFacade);
        verifyNoInteractions(offerFacade);

        assertEquals("Statuses are OK", expectedResponse.getStatus(), response.getStatus());

        ApplicationFullResponse responseEntity = (ApplicationFullResponse) response.getEntity();
        assertEquals("ID is same", applicationFullResponse.getId(), responseEntity.getId());
        assertEquals("Amount is same", applicationFullResponse.getAmount(), responseEntity.getAmount());
        assertEquals("Email is same", applicationFullResponse.getEmail(), responseEntity.getEmail());
        assertEquals("Phone Number is same", applicationFullResponse.getPhoneNumber(), responseEntity.getPhoneNumber());
        assertEquals("Organization ID is same", applicationFullResponse.getOrganization().getId(), responseEntity.getOrganization().getId());
        // TODO add other assert equals with organization
    }

    @Test
    public void createApplicationOffer() {
        // Given
        User user = MockUser.simpleManager();
        String applicationId = "1";
        OfferCreateRequest offerCreateRequest = MockOfferCreateRequest.simpleCreateRequest();
        OfferCreateResponse offerCreateResponse = MockOfferCreateResponse.simpleCreateResponse();
        Response expectedResponse = Response.ok(offerCreateResponse).build();

        // When
        when(offerFacade.createOffer(user, Long.parseLong(applicationId), offerCreateRequest)).thenReturn(expectedResponse);

        // Then
        final Response response = applicationResource.createApplicationOffer(user, applicationId, offerCreateRequest);
        verify(offerFacade).createOffer(user, Long.parseLong(applicationId), offerCreateRequest);
        verifyNoMoreInteractions(offerFacade);
        verifyNoInteractions(applicationFacade);

        assertEquals("Statuses are OK", expectedResponse.getStatus(), response.getStatus());

        OfferCreateResponse responseEntity = (OfferCreateResponse) response.getEntity();
        assertEquals("Application ID is 1", responseEntity.getApplicationId(), Long.valueOf(1L));
        assertEquals("Offer amount is same", offerCreateResponse.getAmount(), responseEntity.getAmount());
        assertEquals("Term is same", offerCreateResponse.getTerm(), responseEntity.getTerm());
        // TODO add more asserts for remaining
    }

    @Test
    public void negotiateLatestApplicationOffer() {
        // Given
        final User user = MockUser.simpleUser();
        final OfferNegotiateUserRequest negotiateRequest = MockOfferNegotiateUserRequest.simpleNegotiateRequest();
        final OfferFullResponse negotiateResponse = MockOfferFullResponse.simpleFullResponse();
        final Response expectedResponse = Response.accepted(negotiateResponse).build();

        // When
        when(offerFacade.negotiateLatestOfferByUser(user, negotiateRequest)).thenReturn(expectedResponse);

        // Then
        final Response response = applicationResource.negotiateLatestApplicationOffer(user, negotiateRequest);
        verify(offerFacade).negotiateLatestOfferByUser(user, negotiateRequest);
        verifyNoMoreInteractions(offerFacade);
        verifyNoInteractions(applicationFacade);

        assertEquals("Statuses are OK", expectedResponse.getStatus(), response.getStatus());

        final OfferFullResponse responseEntity = (OfferFullResponse) response.getEntity();
        assertEquals("Offer ID is 1", responseEntity.getId(), Long.valueOf(1L));
        assertEquals("Application ID is 1", responseEntity.getApplicationId(), Long.valueOf(1L));
        assertEquals("Offer amount is same", negotiateResponse.getAmount(), responseEntity.getAmount());
        assertEquals("Term is same", negotiateResponse.getTerm(), responseEntity.getTerm());
        // TODO add more asserts for remaining
    }

    @Test
    public void negotiateApplicationOffer() {
        // Given
        final User user = MockUser.simpleManager();
        final String applicationId = "1";
        final OfferNegotiateManagerRequest negotiateRequest = MockOfferNegotiateManagerRequest.simpleNegotiateRequest();

        final OfferFullResponse negotiateResponse = MockOfferFullResponse.simpleFullResponse();
        final Response expectedResponse = Response.accepted(negotiateResponse).build();

        // When
        when(offerFacade.negotiateOfferByManager(user, Long.parseLong(applicationId), negotiateRequest)).thenReturn(expectedResponse);

        // Then
        final Response response = applicationResource.negotiateApplicationOffer(user, applicationId, negotiateRequest);
        verify(offerFacade).negotiateOfferByManager(user, Long.parseLong(applicationId), negotiateRequest);
        verifyNoMoreInteractions(offerFacade);
        verifyNoInteractions(applicationFacade);

        assertEquals("Statuses are OK", expectedResponse.getStatus(), response.getStatus());

        final OfferFullResponse responseEntity = (OfferFullResponse) response.getEntity();
        assertEquals("Offer ID is 1", responseEntity.getId(), Long.valueOf(1L));
        assertEquals("Application ID is 1", responseEntity.getApplicationId(), Long.valueOf(1L));
        assertEquals("Offer amount is same", negotiateResponse.getAmount(), responseEntity.getAmount());
        assertEquals("Term is same", negotiateResponse.getTerm(), responseEntity.getTerm());
        // TODO add more asserts for remaining
    }

    @Test
    public void signLatestApplicationOffer() {
        // Given
        final User user = MockUser.simpleUser();
        final ApplicationFullResponse applicationFullResponse = MockApplicationFullResponse.simpleFullResponseWithOffer();
        final Response expectedResponse = Response.accepted(applicationFullResponse).build();

        // When
        when(offerFacade.signLatestOffer(user)).thenReturn(expectedResponse);

        // Then
        final Response response = applicationResource.signLatestApplicationOffer(user);
        verify(offerFacade).signLatestOffer(user);
        verifyNoMoreInteractions(offerFacade);
        verifyNoInteractions(applicationFacade);

        assertEquals("Statuses are OK", expectedResponse.getStatus(), response.getStatus());

        ApplicationFullResponse responseEntity = (ApplicationFullResponse) response.getEntity();
        assertEquals("ID is same", applicationFullResponse.getId(), responseEntity.getId());
        assertEquals("Amount is same", applicationFullResponse.getAmount(), responseEntity.getAmount());
        assertEquals("Email is same", applicationFullResponse.getEmail(), responseEntity.getEmail());
        assertEquals("Phone Number is same", applicationFullResponse.getPhoneNumber(), responseEntity.getPhoneNumber());
        assertEquals("Organization ID is same", applicationFullResponse.getOrganization().getId(), responseEntity.getOrganization().getId());
        assertEquals("Offer ID is same", applicationFullResponse.getOffer().getId(), responseEntity.getOffer().getId());
        // TODO add other assert equals with organization
    }
}