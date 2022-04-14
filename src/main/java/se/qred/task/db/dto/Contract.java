package se.qred.task.db.dto;

import org.joda.time.DateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "contract")
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

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

    @Column(name = "signed_date")
    private DateTime signedDate;

    public Contract() {

    }

    public Contract(Builder build) {
        this.id = build.id;
        this.userId = build.userId;
        this.organization = build.organization;
        this.amount = build.amount;
        this.term = build.term;
        this.interest = build.interest;
        this.totalCommission = build.totalCommission;
        this.totalAmount = build.totalAmount;
        this.signedDate = build.signedDate;
    }

    public static class Builder {

        private Long id;
        private Long userId;
        private Organization organization;
        private Integer amount;
        private String term;
        private Double interest;
        private Double totalCommission;
        private Double totalAmount;
        private DateTime signedDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder organization(Organization organization) {
            this.organization = organization;
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

        public Contract build() {
            return new Contract(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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
}
