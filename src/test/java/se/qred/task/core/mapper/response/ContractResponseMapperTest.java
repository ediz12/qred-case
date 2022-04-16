package se.qred.task.core.mapper.response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import se.qred.task.api.response.ContractResponse;
import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockContract;
import se.qred.task.base.model.response.MockContractResponse;
import se.qred.task.db.dto.Contract;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContractResponseMapperTest extends BaseMockitoTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ContractResponse.Builder builder;

    private ContractResponseMapper contractResponseMapper;

    @Before
    public void setUp() throws Exception {
        contractResponseMapper = new ContractResponseMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void map() {
        // Given
        final Contract contract = MockContract.getSimpleContract();

        final ContractResponse expectedResponse = MockContractResponse.simpleContractWithOrganization();

        // When
        when(builder.id(contract.getId())
                .amount(contract.getAmount())
                .term(contract.getTerm())
                .interest(contract.getInterest())
                .totalCommission(contract.getTotalCommission())
                .totalAmount(contract.getTotalAmount())
                .signedDate(contract.getSignedDate())
                .organization(new OrganizationResponse.Builder()
                        .id(contract.getOrganization().getId())
                        .build())
                .build())
                .thenReturn(expectedResponse);

        // Then
        final ContractResponse response = contractResponseMapper.map(contract);
        verify(builder).id(contract.getId());
        // TODO verify does not support chaining, fix

        verifyNoMoreInteractions(builder);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        assertEquals(expectedResponse.getOrganization().getId(), response.getOrganization().getId());
        // TODO fill in other asserts
    }
}