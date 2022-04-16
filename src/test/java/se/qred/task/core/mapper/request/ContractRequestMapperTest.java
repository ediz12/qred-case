package se.qred.task.core.mapper.request;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import se.qred.task.api.response.OfferFullResponse;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockContract;
import se.qred.task.base.model.db.MockOrganization;
import se.qred.task.base.model.response.MockOfferFullResponse;
import se.qred.task.db.dto.Contract;
import se.qred.task.db.dto.Organization;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContractRequestMapperTest extends BaseMockitoTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Contract.Builder builder;

    private ContractRequestMapper contractRequestMapper;

    @Before
    public void setUp() throws Exception {
        contractRequestMapper = new ContractRequestMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void map() {
        // Given
        final OfferFullResponse offer = MockOfferFullResponse.simpleFullResponse();
        final String userId = "1";
        final Organization organization = MockOrganization.getSimpleOrganization();

        final Contract expectedContract = MockContract.getCreatedContract();

        // When
        when(builder
                .userId(Long.parseLong(userId))
                .organization(organization)
                .amount(offer.getAmount())
                .term(offer.getTerm())
                .interest(offer.getInterest())
                .totalCommission(offer.getTotalCommission())
                .totalAmount(offer.getTotalAmount())
                .signedDate(new DateTime())
                .build())
                .thenReturn(expectedContract);

        // Then
        final Contract contract = contractRequestMapper.map(offer, userId, organization);
        verify(builder).userId(1L);
        // TODO verify does not support chaining, fix
        verifyNoMoreInteractions(builder);


        assertEquals(expectedContract.getId(), contract.getId());
        assertEquals(expectedContract.getAmount(), contract.getAmount());
        // TODO add other asserts
    }
}