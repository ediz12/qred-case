package se.qred.task.base.model.response;

import org.joda.time.DateTime;
import se.qred.task.api.response.OfferFullResponse;

public class MockOfferFullResponse {

    private MockOfferFullResponse() {

    }

    public static OfferFullResponse simpleFullResponse() {
        return new OfferFullResponse.Builder()
                .id(1L)
                .applicationId(1L)
                .amount(10000)
                .term("Simple term")
                .expirationDate(DateTime.now())
                .interest(0.03)
                .totalAmount(10000.03)
                .totalCommission(10000.0)
                .build();
    }
}
