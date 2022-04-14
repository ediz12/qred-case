package se.qred.task.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllabolagOrganizationDetailResponse {

    @JsonProperty("organization_name")
    private String organizationName;

    @JsonProperty("organization_number")
    private String organizationNumber;

    @JsonProperty("organization_type")
    private String organizationType;

    public AllabolagOrganizationDetailResponse() {

    }

    public AllabolagOrganizationDetailResponse(Builder build) {
        this.organizationName = build.organizationName;
        this.organizationNumber = build.organizationNumber;
        this.organizationType = build.organizationType;
    }

    public static class Builder {

        private String organizationName;
        private String organizationNumber;
        private String organizationType;

        public Builder organizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        public Builder organizationNumber(String organizationNumber) {
            this.organizationNumber = organizationNumber;
            return this;
        }

        public Builder organizationType(String organizationType) {
            this.organizationType = organizationType;
            return this;
        }

        public AllabolagOrganizationDetailResponse build() {
            return new AllabolagOrganizationDetailResponse(this);
        }
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    @Override
    public String toString() {
        return "AllabolagOrganizationDetailResponse{" +
                "organizationName='" + organizationName + '\'' +
                ", organizationNumber='" + organizationNumber + '\'' +
                ", organizationType='" + organizationType + '\'' +
                '}';
    }
}
