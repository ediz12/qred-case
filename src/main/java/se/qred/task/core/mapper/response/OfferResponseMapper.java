package se.qred.task.core.mapper.response;

import se.qred.task.api.response.OfferCreateResponse;
import se.qred.task.api.response.OfferFullResponse;
import se.qred.task.db.dto.Offer;

public class OfferResponseMapper {

    public OfferCreateResponse map(Offer offer) {
        return new OfferCreateResponse.Builder()
                .amount(offer.getAmount())
                .term(offer.getTerm())
                .interest(offer.getInterest())
                .totalCommission(offer.getTotalCommission())
                .totalAmount(offer.getTotalAmount())
                .expirationDate(offer.getExpirationDate())
                .build();
    }

    public OfferFullResponse mapFull(Offer offer) {
        return new OfferFullResponse.Builder()
                .id(offer.getId())
                .applicationId(offer.getApplication().getId())
                .amount(offer.getAmount())
                .term(offer.getTerm())
                .interest(offer.getInterest())
                .totalCommission(offer.getTotalCommission())
                .totalAmount(offer.getTotalAmount())
                .expirationDate(offer.getExpirationDate())
                .build();
    }
}
