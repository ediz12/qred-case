package se.qred.task.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

public class OfferFullResponse {

    @JsonProperty
    private Long id;

    @JsonProperty
    private Long applicationId;

    @JsonProperty
    private Integer amount;

    @JsonProperty
    private String term;

    @JsonProperty
    private Double interest;

    @JsonProperty
    private Double totalCommission;

    @JsonProperty
    private Double totalAmount;

    @JsonProperty
    private DateTime expirationDate;

    public OfferFullResponse(Builder build) {
        this.id = build.id;
        this.applicationId = build.applicationId;
        this.amount = build.amount;
        this.term = build.term;
        this.interest = build.interest;
        this.totalCommission = build.totalCommission;
        this.totalAmount = build.totalAmount;
        this.expirationDate = build.expirationDate;
    }

    public static class Builder {

        private Long id;
        private Long applicationId;
        private Integer amount;
        private String term;
        private Double interest;
        private Double totalCommission;
        private Double totalAmount;
        private DateTime expirationDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder applicationId(Long applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public Builder term(String term) {
            this.term = term;
            return this;
        }

        public Builder interest(Double interest) {
            this.interest = interest;
            return this;
        }

        public Builder totalCommission(Double totalCommission) {
            this.totalCommission = totalCommission;
            return this;
        }

        public Builder totalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder expirationDate(DateTime expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public OfferFullResponse build() {
            return new OfferFullResponse(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "OfferFullResponse{" +
                "id='" + id + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", amount=" + amount +
                ", term='" + term + '\'' +
                ", interest=" + interest +
                ", totalCommission=" + totalCommission +
                ", totalAmount=" + totalAmount +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
