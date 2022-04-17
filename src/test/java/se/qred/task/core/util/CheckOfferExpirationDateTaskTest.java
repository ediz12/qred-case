package se.qred.task.core.util;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockOffer;
import se.qred.task.core.service.ApplicationService;
import se.qred.task.core.service.OfferService;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.Offer;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckOfferExpirationDateTaskTest extends BaseMockitoTest {

    @Mock
    private ApplicationService applicationService;

    @Mock
    private OfferService offerService;

    private CheckOfferExpirationDateTask task;

    @Before
    public void setUp() throws Exception {
        task = new CheckOfferExpirationDateTask(applicationService, offerService);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void whenRunWhenOffersAreEmpty_thenDoNothing() {
        // Given
        List<Offer> offers = Lists.newArrayList();

        // When
        when(offerService.getOffersByPending()).thenReturn(offers);

        // Then
        task.run();
        verify(offerService).getOffersByPending();
        verifyNoMoreInteractions(offerService);
        verifyNoInteractions(applicationService);
    }

    @Test
    public void whenRunWhenOffersAreNotEmpty_thenFilterAndCancelApplicationOffers() {
        // Given
        final List<Offer> offers = MockOffer.getOffersWithExpires();
        final List<Offer> expectedExpiredOffers = offers.stream()
                .filter(offer -> offer.getExpirationDate().isBefore(DateTime.now()))
                .collect(Collectors.toList());
        final List<Long> expectedExpiredApplicationIds = expectedExpiredOffers.stream()
                .map(Offer::getApplication)
                .map(Application::getId)
                .collect(Collectors.toList());

        // When
        when(offerService.getOffersByPending()).thenReturn(offers);
        doNothing().when(offerService).expireOffers(expectedExpiredOffers);
        doNothing().when(applicationService).cancelApplications(expectedExpiredApplicationIds);

        // Then
        task.run();
        verify(offerService).getOffersByPending();
        verify(offerService).expireOffers(expectedExpiredOffers);
        verify(applicationService).cancelApplications(expectedExpiredApplicationIds);
        verifyNoMoreInteractions(applicationService, offerService);
    }

    @Test(expected = Exception.class)
    public void whenThereIsError_ThenCatchError() {
        // Given
        final List<Offer> offers = MockOffer.getOffersWithExpires();

        // When
        doThrow(new Throwable("Some error happened")).when(offerService).expireOffers(offers);

        // Then

        Exception exception = null;

        try {
            task.run();
        } catch (Exception t) {
            exception = t;
            t.printStackTrace();
        }

        verify(offerService).getOffersByPending();
        verifyNoMoreInteractions(offerService);
        verifyNoInteractions(applicationService);
        assertNotNull(exception);
    }
}