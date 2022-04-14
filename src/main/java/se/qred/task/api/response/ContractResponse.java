package se.qred.task.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

public class ContractResponse {

    @JsonProperty
    private Long id;

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
    private DateTime signedDate;

    @JsonProperty
    private OrganizationResponse organization;

    public ContractResponse(Builder build) {
        this.id = build.id;
        this.amount = build.amount;
        this.term = build.term;
        this.interest = build.interest;
        this.totalCommission = build.totalCommission;
        this.totalAmount = build.totalAmount;
        this.signedDate = build.signedDate;
        this.organization = build.organization;
    }

    public static class Builder {

        private Long id;
        private Integer amount;
        private String term;
        private Double interest;
        private Double totalCommission;
        private Double totalAmount;
        private DateTime signedDate;
        private OrganizationResponse organization;

        public Builder id(Long id) {
            this.id = id;
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

        public Builder signedDate(DateTime signedDate) {
            this.signedDate = signedDate;
            return this;
        }

        public Builder organization(OrganizationResponse organization) {
            this.organization = organization;
            return this;
        }

        public ContractResponse build() {
            return new ContractResponse(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public DateTime getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(DateTime signedDate) {
        this.signedDate = signedDate;
    }

    public OrganizationResponse getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationResponse organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "ContractResponse{" +
                "id=" + id +
                ", amount=" + amount +
                ", term='" + term + '\'' +
                ", interest=" + interest +
                ", totalCommission=" + totalCommission +
                ", totalAmount=" + totalAmount +
                ", signedDate=" + signedDate +
                ", organization=" + organization +
                '}';
    }
}
