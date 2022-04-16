package se.qred.task.base.model.request;

import se.qred.task.api.request.OfferCreateRequest;

public class MockOfferCreateRequest {

    private MockOfferCreateRequest() {

    }

    public static OfferCreateRequest simpleCreateRequest() {
        return new OfferCreateRequest.Builder()
                .amount(10000)
                .term("Simple term")
                .build();
    }
}
