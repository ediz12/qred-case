package se.qred.task.core.mapper.request;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.client.MockAllabolagOrganizationResponse;
import se.qred.task.base.model.db.MockOrganization;
import se.qred.task.client.model.AllabolagOrganizationDetailResponse;
import se.qred.task.db.dto.Organization;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OrganizationRequestMapperTest extends BaseMockitoTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Organization.Builder builder;

    private OrganizationRequestMapper organizationRequestMapper;

    @Before
    public void setUp() throws Exception {
        organizationRequestMapper = new OrganizationRequestMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void map() {
        // Given
        final AllabolagOrganizationDetailResponse allabolagOrganization = MockAllabolagOrganizationResponse.simpleOrganization();

        final Organization expectedOrganization = MockOrganization.getSimpleModifiedOrganization();

        // When
        when(builder.organizationName(allabolagOrganization.getOrganizationName())
                .organizationNumber(allabolagOrganization.getOrganizationNumber())
                .organizationType(allabolagOrganization.getOrganizationType())
                .build())
                .thenReturn(expectedOrganization);

        // Then
        final Organization organization = organizationRequestMapper.map(allabolagOrganization);
        verify(builder).organizationName(allabolagOrganization.getOrganizationName());
        // TODO verify does not support chaining, fix
        verifyNoMoreInteractions(builder);

        assertEquals(expectedOrganization.getOrganizationName(), organization.getOrganizationName());
        assertEquals(expectedOrganization.getOrganizationNumber(), organization.getOrganizationNumber());
        assertEquals(expectedOrganization.getOrganizationType(), organization.getOrganizationType());
    }
}