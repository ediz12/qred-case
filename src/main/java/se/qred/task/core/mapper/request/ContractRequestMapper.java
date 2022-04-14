package se.qred.task.core.mapper.request;

import org.joda.time.DateTime;
import se.qred.task.api.response.OfferFullResponse;
import se.qred.task.db.dto.Contract;
import se.qred.task.db.dto.Organization;

public class ContractRequestMapper {

    public Contract map(OfferFullResponse signedOffer, String userId, Organization organization) {
        return new Contract.Builder()
                .userId(Long.parseLong(userId))
                .organization(organization)
                .amount(signedOffer.getAmount())
                .term(signedOffer.getTerm())
                .interest(signedOffer.getInterest())
                .totalCommission(signedOffer.getTotalCommission())
                .totalAmount(signedOffer.getTotalAmount())
                .signedDate(new DateTime())
                .build();
    }
}
