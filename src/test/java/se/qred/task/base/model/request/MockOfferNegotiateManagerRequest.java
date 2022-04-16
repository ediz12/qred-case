package se.qred.task.base.model.request;

import se.qred.task.api.request.OfferNegotiateManagerRequest;

public class MockOfferNegotiateManagerRequest {

    private MockOfferNegotiateManagerRequest() {

    }

    public static OfferNegotiateManagerRequest simpleNegotiateRequest() {
        return new OfferNegotiateManagerRequest.Builder()
                .amount(50000)
                .term("Simple term")
                .build();
    }
}
