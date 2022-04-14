package se.qred.task.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import se.qred.task.core.validator.EmailValidation;
import se.qred.task.core.validator.SwedishOrganizationNumberValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ApplicationApplyRequest {

    @Min(10000)
    @Max(250000)
    @JsonProperty
    private Integer amount;

    @NotEmpty
    @JsonProperty
    @EmailValidation
    private String email;

    @NotEmpty
    @JsonProperty
    private String phoneNumber;

    @NotEmpty
    @JsonProperty
    @SwedishOrganizationNumberValidation
    private String organizationNumber;

    public ApplicationApplyRequest() {

    }

    public ApplicationApplyRequest(Builder build) {
        this.amount = build.amount;
        this.email = build.email;
        this.phoneNumber = build.phoneNumber;
        this.organizationNumber = build.organizationNumber;
    }

    public static class Builder {

        private Integer amount;
        private String email;
        private String phoneNumber;
        private String organizationNumber;

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

        public Builder organizationNumber(String organizationNumber) {
            this.organizationNumber = organizationNumber;
            return this;
        }

        public ApplicationApplyRequest build() {
            return new ApplicationApplyRequest(this);
        }
    }

    public Integer getAmount() {
        return amount;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOrganizationNumber() {
        return organizationNumber;
    }

    @Override
    public String toString() {
        return "ApplicationRequest{" +
                "amount=" + amount +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", organizationNumber='" + organizationNumber + '\'' +
                '}';
    }
}
