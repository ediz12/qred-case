package se.qred.task.db.dto;

import org.joda.time.DateTime;
import se.qred.task.core.model.enums.ApplicationStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "application")
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne()
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "amount")
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status")
    private ApplicationStatus status;

    @Column(name = "applied_date")
    private DateTime appliedDate;

    @OneToOne(mappedBy = "application")
    private Offer offer;

    public Application() {

    }

    public Application(Builder build) {
        this.id = build.id;
        this.userId = build.userId;
        this.organization = build.organization;
        this.email = build.email;
        this.phoneNumber = build.phoneNumber;
        this.amount = build.amount;
        this.status = build.status;
        this.appliedDate = build.appliedDate;
    }

    public static class Builder {

        private Long id;
        private String userId;
        private Organization organization;
        private String email;
        private String phoneNumber;
        private Integer amount;
        private ApplicationStatus status;
        private DateTime appliedDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder organization(Organization organization) {
            this.organization = organization;
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

        public Builder amount(Integer amount) {
            this.amount = amount;
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

        public Application build() {
            return new Application(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public DateTime getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(DateTime appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        offer.setApplication(this);
        this.offer = offer;
    }
}
