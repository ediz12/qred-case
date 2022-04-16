package se.qred.task.resources;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import se.qred.task.api.response.ContractResponse;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.core.facade.ContractFacade;
import se.qred.task.db.dto.User;
import se.qred.task.base.model.db.MockUser;
import se.qred.task.base.model.response.MockContractResponse;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContractResourceTest extends BaseMockitoTest {

    @Mock
    private ContractFacade contractFacade;

    private ContractResource contractResource;

    @Before
    public void setUp() throws Exception {
        contractResource = new ContractResource(contractFacade);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getContracts() {
        // Given
        final User user = MockUser.simpleUser();
        final List<ContractResponse> contracts = MockContractResponse.simpleContracts();
        final Response expectedResponse = Response.ok(contracts).build();

        // When
        when(contractFacade.getContractsByUser(user)).thenReturn(expectedResponse);

        // Then
        final Response response = contractResource.getContracts(user);
        verify(contractFacade).getContractsByUser(user);
        verifyNoMoreInteractions(contractFacade);

        assertEquals("Statuses are OK", expectedResponse.getStatus(), response.getStatus());

        List<ContractResponse> responseEntity = (List<ContractResponse>) response.getEntity();
        assertEquals("There is one item", responseEntity.size(), 1);
        final ContractResponse firstContract = responseEntity.get(0);
        final ContractResponse expectedFirstContract = contracts.get(0);
        assertEquals("Contract ID is 1", firstContract.getId(), Long.valueOf(1L));
        assertEquals("Offer amount is same", firstContract.getAmount(), expectedFirstContract.getAmount());
        assertEquals("Term is same", firstContract.getTerm(), expectedFirstContract.getTerm());
        // TODO add more asserts for remaining
    }
}