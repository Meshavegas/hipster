package com.vegas.org.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Accounts.
 */
@Entity
@Table(name = "accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "currency")
    private String currency;

    @Column(name = "create_at")
    private ZonedDateTime createAt;

    @Column(name = "update_at")
    private ZonedDateTime updateAt;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_by")
    private String updateBy;

    /**
     * A relationship
     */
    @NotNull
    @Schema(description = "A relationship")
    @OneToMany(mappedBy = "accountId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accountId" }, allowSetters = true)
    private Set<AccountingJournal> accountingJournals = new HashSet<>();

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties(value = { "accounts" }, allowSetters = true)
    private Users userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Accounts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public Accounts accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return this.balance;
    }

    public Accounts balance(Double balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return this.currency;
    }

    public Accounts currency(String currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ZonedDateTime getCreateAt() {
        return this.createAt;
    }

    public Accounts createAt(ZonedDateTime createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public void setCreateAt(ZonedDateTime createAt) {
        this.createAt = createAt;
    }

    public ZonedDateTime getUpdateAt() {
        return this.updateAt;
    }

    public Accounts updateAt(ZonedDateTime updateAt) {
        this.setUpdateAt(updateAt);
        return this;
    }

    public void setUpdateAt(ZonedDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Accounts createBy(String createBy) {
        this.setCreateBy(createBy);
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Accounts updateBy(String updateBy) {
        this.setUpdateBy(updateBy);
        return this;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Set<AccountingJournal> getAccountingJournals() {
        return this.accountingJournals;
    }

    public void setAccountingJournals(Set<AccountingJournal> accountingJournals) {
        if (this.accountingJournals != null) {
            this.accountingJournals.forEach(i -> i.setAccountId(null));
        }
        if (accountingJournals != null) {
            accountingJournals.forEach(i -> i.setAccountId(this));
        }
        this.accountingJournals = accountingJournals;
    }

    public Accounts accountingJournals(Set<AccountingJournal> accountingJournals) {
        this.setAccountingJournals(accountingJournals);
        return this;
    }

    public Accounts addAccountingJournal(AccountingJournal accountingJournal) {
        this.accountingJournals.add(accountingJournal);
        accountingJournal.setAccountId(this);
        return this;
    }

    public Accounts removeAccountingJournal(AccountingJournal accountingJournal) {
        this.accountingJournals.remove(accountingJournal);
        accountingJournal.setAccountId(null);
        return this;
    }

    public Users getUserId() {
        return this.userId;
    }

    public void setUserId(Users users) {
        this.userId = users;
    }

    public Accounts userId(Users users) {
        this.setUserId(users);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accounts)) {
            return false;
        }
        return id != null && id.equals(((Accounts) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accounts{" +
                "id=" + getId() +
                ", accountNumber='" + getAccountNumber() + "'" +
                ", balance=" + getBalance() +
                ", currency='" + getCurrency() + "'" +
                ", createAt='" + getCreateAt() + "'" +
                ", updateAt='" + getUpdateAt() + "'" +
                ", createBy='" + getCreateBy() + "'" +
                ", updateBy='" + getUpdateBy() + "'" +
                "}";
    }
}
