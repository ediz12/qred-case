package se.qred.task.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationApplyResponse {
    
    @JsonProperty
    private Long id;

    @JsonProperty
    private Integer amount;

    @JsonProperty
    private String email;

    @JsonProperty
    private String phoneNumber;

    @JsonProperty
    private OrganizationResponse organization;

    public ApplicationApplyResponse(Builder builder) {
        this.id = builder.id;
        this.amount = builder.amount;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.organization = builder.organization;
    }

    public static class Builder {

        private Long id;
        private Integer amount;
        private String email;
        private String phoneNumber;
        private OrganizationResponse organization;

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
        
        public ApplicationApplyResponse build() {
            return new ApplicationApplyResponse(this);
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

    @Override
    public String toString() {
        return "ApplicationApplyResponse{" +
                "id=" + id +
                ", amount=" + amount +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", organization=" + organization +
                '}';
    }
}
