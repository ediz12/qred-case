package se.qred.task.core.model;

import se.qred.task.api.response.OfferCreateResponse;
import se.qred.task.db.dto.Offer;

public class OfferPair {

    private final Offer offer;
    private final OfferCreateResponse offerCreateResponse;

    public OfferPair(Offer offer, OfferCreateResponse offerCreateResponse) {
        this.offer = offer;
        this.offerCreateResponse = offerCreateResponse;
    }

    public Offer getOffer() {
        return offer;
    }

    public OfferCreateResponse getOfferCreateResponse() {
        return offerCreateResponse;
    }
}
