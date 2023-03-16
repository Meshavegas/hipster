package com.vegas.org.repository;

import com.vegas.org.domain.AccountingJournal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccountingJournal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountingJournalRepository extends JpaRepository<AccountingJournal, Long> {}
