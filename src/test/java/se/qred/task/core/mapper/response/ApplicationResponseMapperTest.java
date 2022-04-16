package se.qred.task.core.mapper.response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import se.qred.task.api.response.ApplicationApplyResponse;
import se.qred.task.api.response.ApplicationFullResponse;
import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockApplication;
import se.qred.task.base.model.response.MockApplicationApplyResponse;
import se.qred.task.base.model.response.MockApplicationFullResponse;
import se.qred.task.db.dto.Application;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ApplicationResponseMapperTest extends BaseMockitoTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ApplicationApplyResponse.Builder applyBuilder;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ApplicationFullResponse.Builder fullBuilder;

    private ApplicationResponseMapper applicationResponseMapper;

    @Before
    public void setUp() throws Exception {
        applicationResponseMapper = new ApplicationResponseMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void map() {
        // Given
        final Application application = MockApplication.simpleCreatedApplication();

        final ApplicationApplyResponse expectedResponse = MockApplicationApplyResponse.simpleApplyResponse();

        // When
        when(applyBuilder.amount(application.getAmount())
                .email(application.getEmail())
                .phoneNumber(application.getPhoneNumber())
                .organization(new OrganizationResponse.Builder()
                        .id(application.getOrganization().getId())
                        .build())
                .build())
                .thenReturn(expectedResponse);

        // Then
        final ApplicationApplyResponse response = applicationResponseMapper.map(application);
        verify(applyBuilder).amount(application.getAmount());
        // TODO verify does not support chaining, fix

        verifyNoMoreInteractions(applyBuilder);
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        // TODO fill other asserts
    }

    @Test
    public void whenApplicationHasOffer_thenReturnFullResponseWithOffer() {
        // Given
        final Application application = MockApplication.simplePendingApplicationWithOffer();

        final ApplicationFullResponse expectedResponse = MockApplicationFullResponse.simpleFullResponseWithOffer();

        // When
        when(fullBuilder.id(application.getId())
                .amount(application.getAmount())
                .email(application.getEmail())
                .phoneNumber(application.getPhoneNumber())
                .status(application.getStatus())
                .appliedDate(application.getAppliedDate())
                .organization(new OrganizationResponse.Builder()
                        .id(application.getOrganization().getId())
                        .build())
                .build())
                .thenReturn(expectedResponse);

        // Then
        final ApplicationFullResponse response = applicationResponseMapper.mapFull(application);
        verify(fullBuilder).id(application.getId());
        // TODO verify does not support chaining, fix

        verifyNoMoreInteractions(fullBuilder);
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getOffer().getId(),response.getOffer().getId());
        // TODO fill other asserts
    }

    @Test
    public void whenApplicationHasNoOffer_thenReturnFullResponseWithoutOffer() {
        // Given
        final Application application = MockApplication.simpleProcessedApplication();

        final ApplicationFullResponse expectedResponse = MockApplicationFullResponse.simpleFullResponse();

        // When
        when(fullBuilder.id(application.getId())
                .amount(application.getAmount())
                .email(application.getEmail())
                .phoneNumber(application.getPhoneNumber())
                .status(application.getStatus())
                .appliedDate(application.getAppliedDate())
                .organization(new OrganizationResponse.Builder()
                        .id(application.getOrganization().getId())
                        .build())
                .build())
                .thenReturn(expectedResponse);

        // Then
        final ApplicationFullResponse response = applicationResponseMapper.mapFull(application);
        verify(fullBuilder).id(application.getId());
        // TODO verify does not support chaining, fix

        verifyNoMoreInteractions(fullBuilder);
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        assertEquals(expectedResponse.getId(), response.getId());
        assertNull(expectedResponse.getOffer());
        // TODO fill other asserts
    }
}