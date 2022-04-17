package se.qred.task.core.util;

import io.dropwizard.hibernate.UnitOfWork;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.Offer;
import se.qred.task.core.service.ApplicationService;
import se.qred.task.core.service.OfferService;

import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class CheckOfferExpirationDateTask extends TimerTask {

    private static final Logger logger  = LoggerFactory.getLogger(CheckOfferExpirationDateTask.class);
    private final ApplicationService applicationService;
    private final OfferService offerService;

    public CheckOfferExpirationDateTask(ApplicationService applicationService, OfferService offerService) {
        this.applicationService = applicationService;
        this.offerService = offerService;
    }

    @Override
    @UnitOfWork
    public void run() {
        try {
            final List<Offer> offers = offerService.getOffersByPending();
            logger.debug(String.format("Checking offers: %s", offers));
            if (!offers.isEmpty()) {
                final List<Offer> expiredOffers = offers.stream()
                        .filter(offer -> offer.getExpirationDate().isBefore(DateTime.now()))
                        .collect(Collectors.toList());

                final List<Long> expiredApplicationIds = expiredOffers.stream()
                        .map(Offer::getApplication)
                        .map(Application::getId)
                        .collect(Collectors.toList());

                logger.debug(String.format("Going to expire the following offers: %s, applicationIds: %s", offers, expiredApplicationIds));
                offerService.expireOffers(expiredOffers);
                applicationService.cancelApplications(expiredApplicationIds);
            }
        } catch (Exception e) {
            logger.error("An error occured during check offer expiration", e);
        }
    }
}
