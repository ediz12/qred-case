package se.qred.task.core.facade;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import se.qred.task.api.response.ContractResponse;
import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockOrganization;
import se.qred.task.base.model.db.MockUser;
import se.qred.task.base.model.response.MockContractResponse;
import se.qred.task.base.model.response.MockOrganizationResponse;
import se.qred.task.core.service.ContractService;
import se.qred.task.core.service.OrganizationService;
import se.qred.task.db.dto.Organization;
import se.qred.task.db.dto.User;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContractFacadeTest extends BaseMockitoTest {

    @Mock
    private ContractService contractService;

    @Mock
    private OrganizationService organizationService;

    private ContractFacade contractFacade;

    @Before
    public void setUp() throws Exception {
        contractFacade = new ContractFacade(contractService, organizationService);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getContractsByUser() {
        // Given
        final User user = MockUser.simpleUser();
        final List<ContractResponse> initialContractResponses = MockContractResponse.contractResponsesWithOrganization();
        final Organization organization = MockOrganization.getSimpleModifiedOrganization();
        final OrganizationResponse organizationResponse = MockOrganizationResponse.simpleModifiedOrganization();
        final ArrayList<ContractResponse> expectedContracts = Lists.newArrayList(initialContractResponses);
        expectedContracts.forEach(
                contractResponse -> contractResponse.setOrganization(organizationResponse));
        final Response expectedResponse = Response.ok(expectedContracts).build();

        // When
        when(contractService.getContractsByUserId(user.getId())).thenReturn(initialContractResponses);
        when(organizationService.getByOrganizationId(organization.getId())).thenReturn(organizationResponse);

        // Then
        final Response response = contractFacade.getContractsByUser(user);

        verify(contractService).getContractsByUserId(user.getId());
        verify(organizationService, times(initialContractResponses.size())).getByOrganizationId(organization.getId());
        verifyNoMoreInteractions(contractService, organizationService);

        assertEquals(expectedResponse.getStatus(), response.getStatus());

        List<ContractResponse> contractResponses = (List<ContractResponse>) response.getEntity();
        for (int index = 0; index < expectedContracts.size(); index++) {
            final ContractResponse contract = contractResponses.get(index);
            final ContractResponse expectedContract = expectedContracts.get(index);
            assertEquals(expectedContract.getId(), contract.getId());
            assertEquals(expectedContract.getAmount(), contract.getAmount());
            assertEquals(expectedContract.getInterest(), contract.getInterest());
            assertEquals(expectedContract.getTerm(), contract.getTerm());
            assertEquals(expectedContract.getSignedDate(), contract.getSignedDate());
            assertEquals(expectedContract.getOrganization().getId(), contract.getOrganization().getId());
            assertEquals(expectedContract.getOrganization().getName(), contract.getOrganization().getName());
            // TODO fill other asserts
        }
    }
}