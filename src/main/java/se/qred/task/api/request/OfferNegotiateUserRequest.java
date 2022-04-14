package se.qred.task.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class OfferNegotiateUserRequest {

    @Min(10000)
    @Max(250000)
    @JsonProperty
    private Integer amount;

    @NotEmpty
    @JsonProperty
    private String term;

    public OfferNegotiateUserRequest() {

    }

    public OfferNegotiateUserRequest(Builder build) {
        this.amount = build.amount;
        this.term = build.term;
    }

    public static class Builder {

        private Integer amount;
        private String term;

        public Builder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public Builder term(String email) {
            this.term = email;
            return this;
        }

        public OfferNegotiateUserRequest build() {
            return new OfferNegotiateUserRequest(this);
        }
    }


    public Integer getAmount() {
        return amount;
    }

    public String getTerm() {
        return term;
    }
}
