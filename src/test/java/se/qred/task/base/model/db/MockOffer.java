package se.qred.task.base.model.db;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import se.qred.task.core.model.enums.OfferStatus;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.Offer;

import java.util.List;

public class MockOffer {

    private MockOffer() {

    }

    public static Offer getSimpleOffer() {
        return new Offer.Builder()
                .id(1L)
                .amount(10000)
                .term("Simple term")
                .expirationDate(new DateTime().minusMinutes(1))
                .interest(0.03)
                .totalAmount(10000.03)
                .totalCommission(10000.0)
                .offerStatus(OfferStatus.PENDING)
                .application(new Application.Builder()
                        .id(1L)
                        .build())
                .build();
    }

    public static Offer getNegotiatedUserOffer() {
        return new Offer.Builder()
                .id(1L)
                .amount(25000)
                .term("Simple term")
                .expirationDate(new DateTime().minusMinutes(1))
                .interest(0.03)
                .totalAmount(10000.03)
                .totalCommission(10000.0)
                .offerStatus(OfferStatus.PENDING)
                .application(new Application.Builder()
                        .id(1L)
                        .build())
                .build();
    }

    public static Offer getNegotiatedManagerOffer() {
        return new Offer.Builder()
                .id(1L)
                .amount(50000)
                .term("Simple term")
                .expirationDate(new DateTime().minusMinutes(1))
                .interest(0.03)
                .totalAmount(10000.03)
                .totalCommission(10000.0)
                .offerStatus(OfferStatus.PENDING)
                .application(new Application.Builder()
                        .id(1L)
                        .build())
                .build();
    }

    public static List<Offer> getOffersWithExpires() {
        return Lists.newArrayList(getSimpleOffer());
    }
}
