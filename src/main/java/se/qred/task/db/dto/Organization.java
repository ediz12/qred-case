package se.qred.task.db.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "organization")
public class Organization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String organizationName;

    @Column(name = "number", unique = true)
    private String organizationNumber;

    @Column(name = "type")
    private String organizationType;

    @OneToMany(mappedBy = "organization")
    private Set<Application> applications;

    @OneToOne(mappedBy = "organization")
    private Contract contract;

    public Organization() {

    }

    public Organization(Builder build) {
        this.id = build.id;
        this.organizationName = build.organizationName;
        this.organizationNumber = build.organizationNumber;
        this.organizationType = build.organizationType;
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

        public Organization build() {
            return new Organization(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(String organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
