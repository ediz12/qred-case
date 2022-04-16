package se.qred.task.core.mapper.response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import se.qred.task.api.response.OfferCreateResponse;
import se.qred.task.api.response.OfferFullResponse;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockOffer;
import se.qred.task.base.model.response.MockOfferCreateResponse;
import se.qred.task.base.model.response.MockOfferFullResponse;
import se.qred.task.db.dto.Offer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OfferResponseMapperTest extends BaseMockitoTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private OfferFullResponse.Builder fullBuilder;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private OfferCreateResponse.Builder createBuilder;

    private OfferResponseMapper offerResponseMapper;

    @Before
    public void setUp() throws Exception {
        offerResponseMapper = new OfferResponseMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void map() {
        // Given
        final Offer offer = MockOffer.getSimpleOffer();

        final OfferCreateResponse expectedResponse = MockOfferCreateResponse.simpleCreateResponse();
        // When
        when(createBuilder.amount(offer.getAmount())
                .term(offer.getTerm())
                .interest(offer.getInterest())
                .totalCommission(offer.getTotalCommission())
                .totalAmount(offer.getTotalAmount())
                .expirationDate(offer.getExpirationDate())
                .build())
                .thenReturn(expectedResponse);

        // Then
        final OfferCreateResponse response = offerResponseMapper.map(offer);
        verify(createBuilder).amount(offer.getAmount());
        // TODO verify does not support chaining, fix

        verifyNoMoreInteractions(createBuilder);
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        assertEquals(expectedResponse.getTerm(), response.getTerm());
        // TODO fill other asserts
    }

    @Test
    public void mapFull() {
        // Given
        final Offer offer = MockOffer.getSimpleOffer();

        final OfferFullResponse expectedResponse = MockOfferFullResponse.simpleFullResponse();

        // When
        when(fullBuilder.id(offer.getId())
                .applicationId(offer.getApplication().getId())
                .amount(offer.getAmount())
                .term(offer.getTerm())
                .interest(offer.getInterest())
                .totalCommission(offer.getTotalCommission())
                .totalAmount(offer.getTotalAmount())
                .expirationDate(offer.getExpirationDate())
                .build())
                .thenReturn(expectedResponse);

        // Then
        final OfferFullResponse response = offerResponseMapper.mapFull(offer);
        verify(fullBuilder).id(offer.getId());
        // TODO verify does not support chaining, fix

        verifyNoMoreInteractions(fullBuilder);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        assertEquals(expectedResponse.getTerm(), response.getTerm());
        // TODO fill other asserts
    }
}