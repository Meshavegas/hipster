package com.vegas.org.service.impl;

import com.vegas.org.domain.AccountingJournal;
import com.vegas.org.repository.AccountingJournalRepository;
import com.vegas.org.service.AccountingJournalService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccountingJournal}.
 */
@Service
@Transactional
public class AccountingJournalServiceImpl implements AccountingJournalService {

    private final Logger log = LoggerFactory.getLogger(AccountingJournalServiceImpl.class);

    private final AccountingJournalRepository accountingJournalRepository;

    public AccountingJournalServiceImpl(AccountingJournalRepository accountingJournalRepository) {
        this.accountingJournalRepository = accountingJournalRepository;
    }

    @Override
    public AccountingJournal save(AccountingJournal accountingJournal) {
        log.debug("Request to save AccountingJournal : {}", accountingJournal);
        return accountingJournalRepository.save(accountingJournal);
    }

    @Override
    public AccountingJournal update(AccountingJournal accountingJournal) {
        log.debug("Request to update AccountingJournal : {}", accountingJournal);
        return accountingJournalRepository.save(accountingJournal);
    }

    @Override
    public Optional<AccountingJournal> partialUpdate(AccountingJournal accountingJournal) {
        log.debug("Request to partially update AccountingJournal : {}", accountingJournal);

        return accountingJournalRepository
            .findById(accountingJournal.getId())
            .map(existingAccountingJournal -> {
                if (accountingJournal.getDirection() != null) {
                    existingAccountingJournal.setDirection(accountingJournal.getDirection());
                }
                if (accountingJournal.getAmount() != null) {
                    existingAccountingJournal.setAmount(accountingJournal.getAmount());
                }
                if (accountingJournal.getBalanceBefore() != null) {
                    existingAccountingJournal.setBalanceBefore(accountingJournal.getBalanceBefore());
                }
                if (accountingJournal.getBalanceAfter() != null) {
                    existingAccountingJournal.setBalanceAfter(accountingJournal.getBalanceAfter());
                }
                if (accountingJournal.getCreateAt() != null) {
                    existingAccountingJournal.setCreateAt(accountingJournal.getCreateAt());
                }
                if (accountingJournal.getUpdateAt() != null) {
                    existingAccountingJournal.setUpdateAt(accountingJournal.getUpdateAt());
                }
                if (accountingJournal.getCreateBy() != null) {
                    existingAccountingJournal.setCreateBy(accountingJournal.getCreateBy());
                }
                if (accountingJournal.getUpdateBy() != null) {
                    existingAccountingJournal.setUpdateBy(accountingJournal.getUpdateBy());
                }

                return existingAccountingJournal;
            })
            .map(accountingJournalRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountingJournal> findAll(Pageable pageable) {
        log.debug("Request to get all AccountingJournals");
        return accountingJournalRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountingJournal> findOne(Long id) {
        log.debug("Request to get AccountingJournal : {}", id);
        return accountingJournalRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountingJournal : {}", id);
        accountingJournalRepository.deleteById(id);
    }
}
