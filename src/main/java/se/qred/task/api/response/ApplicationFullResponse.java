package se.qred.task.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import se.qred.task.core.model.enums.ApplicationStatus;

public class ApplicationFullResponse {

    @JsonProperty
    private Long id;

    @JsonProperty
    private Integer amount;

    @JsonProperty
    private String email;

    @JsonProperty
    private String phoneNumber;

    @JsonProperty
    private ApplicationStatus status;

    @JsonProperty
    private DateTime appliedDate;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrganizationResponse organization;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OfferFullResponse offer;

    public ApplicationFullResponse(Builder builder) {
        this.id = builder.id;
        this.amount = builder.amount;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.organization = builder.organization;
        this.offer = builder.offer;
        this.status = builder.status;
        this.appliedDate = builder.appliedDate;
    }

    public static class Builder {

        private Long id;
        private Integer amount;
        private String email;
        private String phoneNumber;
        private OrganizationResponse organization;
        private OfferFullResponse offer;
        private ApplicationStatus status;
        private DateTime appliedDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder organization(OrganizationResponse organization) {
            this.organization = organization;
            return this;
        }

        public Builder offer(OfferFullResponse offer) {
            this.offer = offer;
            return this;
        }

        public Builder status(ApplicationStatus applicationStatus) {
            this.status = applicationStatus;
            return this;
        }

        public Builder appliedDate(DateTime appliedDate) {
            this.appliedDate = appliedDate;
            return this;
        }

        public ApplicationFullResponse build() {
            return new ApplicationFullResponse(this);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public OrganizationResponse getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationResponse organization) {
        this.organization = organization;
    }

    public OfferFullResponse getOffer() {
        return offer;
    }

    public void setOffer(OfferFullResponse offer) {
        this.offer = offer;
    }
    
    @Override
    public String toString() {
        return "ApplicationFullResponse{" +
                "id=" + id +
                ", amount=" + amount +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                ", appliedDate=" + appliedDate +
                ", organization=" + organization +
                ", offer=" + offer +
                '}';
    }
}
