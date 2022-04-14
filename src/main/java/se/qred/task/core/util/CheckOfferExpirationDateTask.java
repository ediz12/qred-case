package se.qred.task.core.util;

import org.joda.time.DateTime;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.Offer;
import se.qred.task.core.service.ApplicationService;
import se.qred.task.core.service.OfferService;

import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class CheckOfferExpirationDateTask extends TimerTask {

    private final ApplicationService applicationService;
    private final OfferService offerService;

    public CheckOfferExpirationDateTask(ApplicationService applicationService, OfferService offerService) {
        this.applicationService = applicationService;
        this.offerService = offerService;
    }

    @Override
    public void run() {
        final List<Offer> offers = offerService.getOffersByPending();
        if (offers.isEmpty()) {
            return;
        }

        final List<Offer> expiredOffers = offers.stream()
                .filter(offer -> offer.getExpirationDate().isAfter(DateTime.now()))
                .collect(Collectors.toList());

        final List<Long> expiredApplicationIds = offers.stream()
                .map(Offer::getApplication)
                .map(Application::getId)
                .collect(Collectors.toList());

        offerService.expireOffers(expiredOffers);
        applicationService.cancelApplications(expiredApplicationIds);
    }
}
