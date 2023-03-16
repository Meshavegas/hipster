package com.vegas.org.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vegas.org.domain.enumeration.Direction;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Schema(description = "not an ignored comment")
@Entity
@Table(name = "accounting_journal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccountingJournal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction")
    private Direction direction;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "balance_before")
    private Double balanceBefore;

    @Column(name = "balance_after")
    private Double balanceAfter;

    @Column(name = "create_at")
    private ZonedDateTime createAt;

    @Column(name = "update_at")
    private ZonedDateTime updateAt;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_by")
    private String updateBy;

    /**
     * Another side of the same relationship
     */
    @NotNull
    @Schema(description = "Another side of the same relationship")
    @ManyToOne
    @JsonIgnoreProperties(value = { "accountingJournals", "userId" }, allowSetters = true)
    private Accounts accountId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AccountingJournal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public AccountingJournal direction(Direction direction) {
        this.setDirection(direction);
        return this;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Double getAmount() {
        return this.amount;
    }

    public AccountingJournal amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalanceBefore() {
        return this.balanceBefore;
    }

    public AccountingJournal balanceBefore(Double balanceBefore) {
        this.setBalanceBefore(balanceBefore);
        return this;
    }

    public void setBalanceBefore(Double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public Double getBalanceAfter() {
        return this.balanceAfter;
    }

    public AccountingJournal balanceAfter(Double balanceAfter) {
        this.setBalanceAfter(balanceAfter);
        return this;
    }

    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public ZonedDateTime getCreateAt() {
        return this.createAt;
    }

    public AccountingJournal createAt(ZonedDateTime createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public void setCreateAt(ZonedDateTime createAt) {
        this.createAt = createAt;
    }

    public ZonedDateTime getUpdateAt() {
        return this.updateAt;
    }

    public AccountingJournal updateAt(ZonedDateTime updateAt) {
        this.setUpdateAt(updateAt);
        return this;
    }

    public void setUpdateAt(ZonedDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public AccountingJournal createBy(String createBy) {
        this.setCreateBy(createBy);
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public AccountingJournal updateBy(String updateBy) {
        this.setUpdateBy(updateBy);
        return this;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Accounts getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Accounts accounts) {
        this.accountId = accounts;
    }

    public AccountingJournal accountId(Accounts accounts) {
        this.setAccountId(accounts);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountingJournal)) {
            return false;
        }
        return id != null && id.equals(((AccountingJournal) o).id);
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
        return "AccountingJournal{" +
                "id=" + getId() +
                ", direction='" + getDirection() + "'" +
                ", amount=" + getAmount() +
                ", balanceBefore=" + getBalanceBefore() +
                ", balanceAfter=" + getBalanceAfter() +
                ", createAt='" + getCreateAt() + "'" +
                ", updateAt='" + getUpdateAt() + "'" +
                ", createBy='" + getCreateBy() + "'" +
                ", updateBy='" + getUpdateBy() + "'" +
                "}";
    }
}
