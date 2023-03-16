package com.vegas.org.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vegas.org.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountingJournalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountingJournal.class);
        AccountingJournal accountingJournal1 = new AccountingJournal();
        accountingJournal1.setId(1L);
        AccountingJournal accountingJournal2 = new AccountingJournal();
        accountingJournal2.setId(accountingJournal1.getId());
        assertThat(accountingJournal1).isEqualTo(accountingJournal2);
        accountingJournal2.setId(2L);
        assertThat(accountingJournal1).isNotEqualTo(accountingJournal2);
        accountingJournal1.setId(null);
        assertThat(accountingJournal1).isNotEqualTo(accountingJournal2);
    }
}
