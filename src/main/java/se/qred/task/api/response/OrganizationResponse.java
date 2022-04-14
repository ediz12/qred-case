package se.qred.task.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrganizationResponse {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String number;

    @JsonProperty
    private String type;

    public OrganizationResponse(Builder build) {
        this.id = build.id;
        this.name = build.organizationName;
        this.number = build.organizationNumber;
        this.type = build.organizationType;
    }

    public static class Builder {

        private Long id;
        private String organizationName;
        private String organizationNumber;
        private String organizationType;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

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

        public OrganizationResponse build() {
            return new OrganizationResponse(this);
        }
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrganizationResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
