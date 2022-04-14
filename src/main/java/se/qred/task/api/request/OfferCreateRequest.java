package se.qred.task.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

public class OfferCreateRequest {

    @Min(1)
    @JsonProperty
    private Integer amount;

    @NotEmpty
    @JsonProperty
    private String term;

    public OfferCreateRequest() {

    }

    public OfferCreateRequest(Builder build) {
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

        public OfferCreateRequest build() {
            return new OfferCreateRequest(this);
        }
    }

    public Integer getAmount() {
        return amount;
    }

    public String getTerm() {
        return term;
    }

    @Override
    public String toString() {
        return "OfferCreateRequest{" +
                "amount=" + amount +
                ", term='" + term + '\'' +
                '}';
    }
}
