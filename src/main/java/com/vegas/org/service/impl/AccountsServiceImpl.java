package com.vegas.org.service.impl;

import com.vegas.org.domain.Accounts;
import com.vegas.org.repository.AccountsRepository;
import com.vegas.org.service.AccountsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Accounts}.
 */
@Service
@Transactional
public class AccountsServiceImpl implements AccountsService {

    private final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private final AccountsRepository accountsRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public Accounts save(Accounts accounts) {
        log.debug("Request to save Accounts : {}", accounts);
        return accountsRepository.save(accounts);
    }

    @Override
    public Accounts update(Accounts accounts) {
        log.debug("Request to update Accounts : {}", accounts);
        return accountsRepository.save(accounts);
    }

    @Override
    public Optional<Accounts> partialUpdate(Accounts accounts) {
        log.debug("Request to partially update Accounts : {}", accounts);

        return accountsRepository
            .findById(accounts.getId())
            .map(existingAccounts -> {
                if (accounts.getAccountNumber() != null) {
                    existingAccounts.setAccountNumber(accounts.getAccountNumber());
                }
                if (accounts.getBalance() != null) {
                    existingAccounts.setBalance(accounts.getBalance());
                }
                if (accounts.getCurrency() != null) {
                    existingAccounts.setCurrency(accounts.getCurrency());
                }
                if (accounts.getCreateAt() != null) {
                    existingAccounts.setCreateAt(accounts.getCreateAt());
                }
                if (accounts.getUpdateAt() != null) {
                    existingAccounts.setUpdateAt(accounts.getUpdateAt());
                }
                if (accounts.getCreateBy() != null) {
                    existingAccounts.setCreateBy(accounts.getCreateBy());
                }
                if (accounts.getUpdateBy() != null) {
                    existingAccounts.setUpdateBy(accounts.getUpdateBy());
                }

                return existingAccounts;
            })
            .map(accountsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Accounts> findAll(Pageable pageable) {
        log.debug("Request to get all Accounts");
        return accountsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Accounts> findOne(Long id) {
        log.debug("Request to get Accounts : {}", id);
        return accountsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accounts : {}", id);
        accountsRepository.deleteById(id);
    }
}
