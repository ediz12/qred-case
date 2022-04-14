package se.qred.task.db.dto;

import org.joda.time.DateTime;
import se.qred.task.core.model.enums.OfferStatus;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "offer")
public class Offer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne()
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "term")
    private String term;

    @Column(name = "interest")
    private Double interest;

    @Column(name = "total_commission")
    private Double totalCommission;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "expiration_date")
    private DateTime expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_status")
    private OfferStatus offerStatus;

    public Offer() {

    }

    public Offer(Builder build) {
        this.id = build.id;
        this.application = build.application;
        this.amount = build.amount;
        this.term = build.term;
        this.interest = build.interest;
        this.totalCommission = build.totalCommission;
        this.totalAmount = build.totalAmount;
        this.expirationDate = build.expirationDate;
        this.offerStatus = build.offerStatus;
    }

    public static class Builder {

        private long id;
        private Application application;
        private int amount;
        private String term;
        private double interest;
        private double totalCommission;
        private double totalAmount;
        private DateTime expirationDate;
        private OfferStatus offerStatus;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder application(Application application) {
            this.application = application;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder term(String term) {
            this.term = term;
            return this;
        }

        public Builder interest(double interest) {
            this.interest = interest;
            return this;
        }

        public Builder totalCommission(double totalCommission) {
            this.totalCommission = totalCommission;
            return this;
        }

        public Builder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder expirationDate(DateTime expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder offerStatus(OfferStatus offerStatus) {
            this.offerStatus = offerStatus;
            return this;
        }

        public Offer build() {
            return new Offer(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
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

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }
}
