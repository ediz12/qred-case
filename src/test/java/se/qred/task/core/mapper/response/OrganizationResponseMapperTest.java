package se.qred.task.core.mapper.response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockOrganization;
import se.qred.task.base.model.response.MockOrganizationResponse;
import se.qred.task.db.dto.Organization;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OrganizationResponseMapperTest extends BaseMockitoTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private OrganizationResponse.Builder builder;

    private OrganizationResponseMapper organizationResponseMapper;

    @Before
    public void setUp() throws Exception {
        organizationResponseMapper = new OrganizationResponseMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void map() {
        // Given
        final Organization organization = MockOrganization.getSimpleModifiedOrganization();

        final OrganizationResponse expectedResponse = MockOrganizationResponse.simpleModifiedOrganization();

        // When
        when(builder.id(organization.getId())
                .organizationName(organization.getOrganizationName())
                .organizationNumber(organization.getOrganizationNumber())
                .organizationType(organization.getOrganizationType())
                .build())
                .thenReturn(expectedResponse);

        // Then
        final OrganizationResponse organizationResponse = organizationResponseMapper.map(organization);
        verify(builder).id(organization.getId());
        // TODO verify does not support chaining, fix

        verifyNoMoreInteractions(builder);

        assertEquals(expectedResponse.getId(), organization.getId());
        assertEquals(expectedResponse.getName(), organizationResponse.getName());
        assertEquals(expectedResponse.getNumber(), organizationResponse.getNumber());
        assertEquals(expectedResponse.getType(), organizationResponse.getType());
    }
}