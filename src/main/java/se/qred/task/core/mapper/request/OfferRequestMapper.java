package se.qred.task.core.mapper.request;

import org.joda.time.DateTime;
import se.qred.task.api.request.OfferCreateRequest;
import se.qred.task.api.request.OfferNegotiateManagerRequest;
import se.qred.task.api.request.OfferNegotiateUserRequest;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.Offer;
import se.qred.task.core.model.enums.OfferStatus;


public class OfferRequestMapper {

    private static final int MAX_AMOUNT = 250000;
    private static final int EXPIRES_AFTER_DAYS_AMOUNT = 7;

    public Offer map(OfferCreateRequest offerRequest, Application application) {
        final int amount = offerRequest.getAmount();
        final Offer offer = new Offer.Builder()
                .application(application)
                .amount(amount)
                .term(offerRequest.getTerm())
                .interest(getInterestRate(amount))
                .expirationDate(DateTime.now().plusDays(EXPIRES_AFTER_DAYS_AMOUNT))
                .offerStatus(OfferStatus.PENDING)
                .build();

        offer.setTotalCommission(amount + offer.getInterest());
        offer.setTotalAmount(amount + offer.getTotalAmount());
        return offer;
    }

    public Offer map(OfferNegotiateUserRequest negotiateRequest, Offer offer) {
        final int newAmount = negotiateRequest.getAmount();
        offer.setAmount(newAmount);
        offer.setTerm(negotiateRequest.getTerm());
        offer.setInterest(getInterestRate(newAmount));
        offer.setTotalCommission(newAmount + offer.getInterest());
        offer.setTotalAmount(newAmount + offer.getTotalCommission());
        offer.setOfferStatus(OfferStatus.NEGOTIATED);
        return offer;
    }

    public Offer map(OfferNegotiateManagerRequest negotiateRequest, Offer offer) {
        final int newAmount = negotiateRequest.getAmount();
        offer.setAmount(newAmount);
        offer.setTerm(negotiateRequest.getTerm());
        offer.setInterest(getInterestRate(newAmount));
        offer.setTotalCommission(newAmount + offer.getInterest());
        offer.setTotalAmount(newAmount + offer.getTotalCommission());
        offer.setExpirationDate(new DateTime());
        offer.setOfferStatus(OfferStatus.PENDING);
        return offer;
    }

    /**
     * Returns a rough estimate of the interest rate
     *
     * @param amount
     * @return
     */
    private double getInterestRate(int amount) {
        return Math.max(0.06 * (1.0 - (Math.abs((double) amount - MAX_AMOUNT) / MAX_AMOUNT)), 0.03);
    }

}
