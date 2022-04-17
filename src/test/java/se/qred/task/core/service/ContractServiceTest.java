package se.qred.task.core.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import se.qred.task.api.response.ContractResponse;
import se.qred.task.api.response.OfferFullResponse;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockContract;
import se.qred.task.base.model.db.MockOrganization;
import se.qred.task.base.model.response.MockContractResponse;
import se.qred.task.base.model.response.MockOfferFullResponse;
import se.qred.task.core.mapper.request.ContractRequestMapper;
import se.qred.task.core.mapper.response.ContractResponseMapper;
import se.qred.task.db.ContractRepository;
import se.qred.task.db.dto.Contract;
import se.qred.task.db.dto.Organization;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContractServiceTest extends BaseMockitoTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ContractRequestMapper contractRequestMapper;

    @Mock
    private ContractResponseMapper contractResponseMapper;

    private ContractService contractService;

    @Before
    public void setUp() throws Exception {
        contractService = new ContractService(contractRepository, contractRequestMapper, contractResponseMapper);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void create() {
        // Given
        final OfferFullResponse offerResponse = MockOfferFullResponse.simpleFullResponse();
        final String userId = "1";
        final Organization organization = MockOrganization.getSimpleModifiedOrganization();

        final Contract mappedContract = MockContract.getCreatedContract();
        final Contract createdContract = MockContract.getSimpleContract();
        final ContractResponse expectedContractResponse = MockContractResponse.simpleContractWithOrganization();

        // When
        when(contractRequestMapper.map(offerResponse, userId, organization)).thenReturn(mappedContract);
        when(contractRepository.save(mappedContract)).thenReturn(createdContract);
        when(contractResponseMapper.map(createdContract)).thenReturn(expectedContractResponse);

        // Then
        final ContractResponse contractResponse = contractService.create(offerResponse, userId, organization);

        verify(contractRequestMapper).map(offerResponse, userId, organization);
        verify(contractRepository).save(mappedContract);
        verify(contractResponseMapper).map(createdContract);
        verifyNoMoreInteractions(contractRepository, contractRequestMapper, contractResponseMapper);

        assertEquals(expectedContractResponse.getId(), contractResponse.getId());
        assertEquals(expectedContractResponse.getAmount(), contractResponse.getAmount());
        assertEquals(expectedContractResponse.getTerm(), contractResponse.getTerm());
        assertEquals(expectedContractResponse.getOrganization().getId(), contractResponse.getOrganization().getId());
        // TODO fill in other asserts
    }

    @Test
    public void getContractsByUserId() {
        // Given
        final String userId = "1";
        final List<Contract> simpleContracts = MockContract.getSimpleContracts();
        final List<ContractResponse> expectedResponses = MockContractResponse.contractResponsesWithOrganization();

        // When
        when(contractRepository.findAllByUserId(userId)).thenReturn(simpleContracts);
        for (int index = 0; index < simpleContracts.size(); index++) {
            when(contractResponseMapper.map(simpleContracts.get(index))).thenReturn(expectedResponses.get(index));
        }

        // Then
        final List<ContractResponse> responses = contractService.getContractsByUserId(userId);
        verify(contractRepository).findAllByUserId(userId);
        verify(contractResponseMapper, times(simpleContracts.size())).map(any(Contract.class));
        verifyNoMoreInteractions(contractRepository, contractResponseMapper);
        verifyNoInteractions(contractRequestMapper);

        for (int index = 0; index < expectedResponses.size(); index++) {
            final ContractResponse expectedResponse = expectedResponses.get(index);
            final ContractResponse response = responses.get(index);

            assertEquals(expectedResponse.getId(), response.getId());
            assertEquals(expectedResponse.getAmount(), response.getAmount());
            assertEquals(expectedResponse.getTerm(), response.getTerm());
            // TODO fill in other assertions
        }
    }
}