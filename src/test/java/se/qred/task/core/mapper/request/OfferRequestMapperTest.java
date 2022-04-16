package se.qred.task.core.mapper.request;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import se.qred.task.api.request.OfferCreateRequest;
import se.qred.task.api.request.OfferNegotiateManagerRequest;
import se.qred.task.api.request.OfferNegotiateUserRequest;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockOffer;
import se.qred.task.base.model.request.MockOfferCreateRequest;
import se.qred.task.base.model.request.MockOfferNegotiateManagerRequest;
import se.qred.task.base.model.request.MockOfferNegotiateUserRequest;
import se.qred.task.core.model.enums.OfferStatus;
import se.qred.task.db.dto.Offer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OfferRequestMapperTest extends BaseMockitoTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Offer.Builder builder;

    private OfferRequestMapper offerRequestMapper;

    @Before
    public void setUp() throws Exception {
        offerRequestMapper = new OfferRequestMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void mapCreateRequest() {
        // Given
        final OfferCreateRequest offerCreateRequest = MockOfferCreateRequest.simpleCreateRequest();

        final Offer expectedOffer = MockOffer.getSimpleOffer();

        // When
        when(builder
                .amount(offerCreateRequest.getAmount())
                .term(offerCreateRequest.getTerm())
                .expirationDate(new DateTime().minusMinutes(1))
                .interest(getInterestRate(offerCreateRequest.getAmount()))
                .totalCommission(getInterestRate(offerCreateRequest.getAmount()) + offerCreateRequest.getAmount())
                .totalAmount(getInterestRate(offerCreateRequest.getAmount()) + offerCreateRequest.getAmount() + offerCreateRequest.getAmount())
                .offerStatus(OfferStatus.PENDING)
                .build())
                .thenReturn(expectedOffer);

        // Then
        final Offer offer = offerRequestMapper.map(offerCreateRequest);
        verify(builder).amount(offerCreateRequest.getAmount());
        // TODO verify does not support chaining, fix

        verifyNoMoreInteractions(builder);

        assertEquals(expectedOffer.getAmount(), offer.getAmount());
        assertEquals(expectedOffer.getTerm(), offer.getTerm());
        // TODO fill in others
    }

    @Test
    public void mapUserNegotiateRequest() {
        // Given
        final OfferNegotiateUserRequest negotiateRequest = MockOfferNegotiateUserRequest.simpleNegotiateRequest();
        final Offer simpleOffer = MockOffer.getSimpleOffer();

        final Offer negotiatedOffer = MockOffer.getNegotiatedUserOffer();

        // When

        // Then
        final Offer offer = offerRequestMapper.map(negotiateRequest, simpleOffer);
        verifyNoInteractions(builder);

        assertEquals(negotiatedOffer.getAmount(), offer.getAmount());
        assertEquals(negotiatedOffer.getTerm(), offer.getTerm());
        // TODO fill other asserts
    }

    @Test
    public void mapManagerNegotiateRequest() {
        // Given
        final OfferNegotiateManagerRequest negotiateRequest = MockOfferNegotiateManagerRequest.simpleNegotiateRequest();
        final Offer simpleOffer = MockOffer.getSimpleOffer();

        final Offer negotiatedOffer = MockOffer.getNegotiatedManagerOffer();

        // When

        // Then
        final Offer offer = offerRequestMapper.map(negotiateRequest, simpleOffer);
        verifyNoInteractions(builder);

        assertEquals(negotiatedOffer.getAmount(), offer.getAmount());
        assertEquals(negotiatedOffer.getTerm(), offer.getTerm());
        // TODO fill other asserts
    }

    private double getInterestRate(int amount) {
        final int MAX_AMOUNT = 250000;
        return Math.max(0.06 * (1.0 - (Math.abs((double) amount - MAX_AMOUNT) / MAX_AMOUNT)), 0.03);
    }
}