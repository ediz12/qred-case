package se.qred.task.base.model.request;

import se.qred.task.api.request.OfferNegotiateUserRequest;

public class MockOfferNegotiateUserRequest {

    private MockOfferNegotiateUserRequest() {

    }

    public static OfferNegotiateUserRequest simpleNegotiateRequest() {
        return new OfferNegotiateUserRequest.Builder()
                .amount(25000)
                .term("Simple term")
                .build();
    }
}
