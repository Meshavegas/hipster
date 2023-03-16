package com.vegas.org.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vegas.org.config.Constants;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Users.
 */
@Entity
@Table(name = "bk_users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Users extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Email
    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "create_at")
    private ZonedDateTime createAt;

    @Column(name = "update_at")
    private ZonedDateTime updateAt;

    @NotNull
    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_by")
    private String updateBy;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @NotNull
    @OneToMany(mappedBy = "userId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "accountingJournals", "userId" }, allowSetters = true)
    private Set<Accounts> accounts = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Users id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Users name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Users email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Users password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZonedDateTime getCreateAt() {
        return this.createAt;
    }

    public Users createAt(ZonedDateTime createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public void setCreateAt(ZonedDateTime createAt) {
        this.createAt = createAt;
    }

    public ZonedDateTime getUpdateAt() {
        return this.updateAt;
    }

    public Users updateAt(ZonedDateTime updateAt) {
        this.setUpdateAt(updateAt);
        return this;
    }

    public void setUpdateAt(ZonedDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Users createBy(String createBy) {
        this.setCreateBy(createBy);
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Users updateBy(String updateBy) {
        this.setUpdateBy(updateBy);
        return this;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Set<Accounts> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Set<Accounts> accounts) {
        if (this.accounts != null) {
            this.accounts.forEach(i -> i.setUserId(null));
        }
        if (accounts != null) {
            accounts.forEach(i -> i.setUserId(this));
        }
        this.accounts = accounts;
    }

    public Users accounts(Set<Accounts> accounts) {
        this.setAccounts(accounts);
        return this;
    }

    public Users addAccounts(Accounts accounts) {
        this.accounts.add(accounts);
        accounts.setUserId(this);
        return this;
    }

    public Users removeAccounts(Accounts accounts) {
        this.accounts.remove(accounts);
        accounts.setUserId(null);
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Users)) {
            return false;
        }
        return id != null && id.equals(((Users) o).id);
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
        return "Users{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", email='" + getEmail() + "'" +
                ", password='" + getPassword() + "'" +
                ", createAt='" + getCreateAt() + "'" +
                ", updateAt='" + getUpdateAt() + "'" +
                ", createBy='" + getCreateBy() + "'" +
                ", updateBy='" + getUpdateBy() + "'" +
                "}";
    }
}
