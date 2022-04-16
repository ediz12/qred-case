package se.qred.task.core.mapper.request;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import se.qred.task.api.request.ApplicationApplyRequest;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockApplication;
import se.qred.task.base.model.db.MockOrganization;
import se.qred.task.base.model.request.MockApplicationApplyRequest;
import se.qred.task.core.model.enums.ApplicationStatus;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.Organization;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ApplicationRequestMapperTest extends BaseMockitoTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Application.Builder builder;

    private ApplicationRequestMapper applicationRequestMapper;

    @Before
    public void setUp() throws Exception {
        applicationRequestMapper = new ApplicationRequestMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void map() {
        // Given
        final ApplicationApplyRequest applyRequest = MockApplicationApplyRequest.simpleApplyRequest();
        final Organization organization = MockOrganization.getSimpleOrganization();
        final String userId = "1";

        final Application expectedApplication = MockApplication.simpleCreatedApplication();

        // When
        when(builder.userId(userId)
                .organization(organization)
                .email(applyRequest.getEmail())
                .phoneNumber(applyRequest.getPhoneNumber())
                .amount(applyRequest.getAmount())
                .status(ApplicationStatus.PROCESSED)
                .appliedDate(new DateTime())
                .build())
                .thenReturn(expectedApplication);

        // Then
        final Application application = applicationRequestMapper.map(applyRequest, organization, userId);
        verify(builder).userId("1");
        // TODO verify does not support chaining, fix

        verifyNoMoreInteractions(builder);

        assertEquals(expectedApplication.getId(), application.getId());
        assertEquals(expectedApplication.getAmount(), application.getAmount());
        // TODO add other asserts
    }
}