package com.vegas.org.web.rest;

import static com.vegas.org.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vegas.org.IntegrationTest;
import com.vegas.org.domain.AccountingJournal;
import com.vegas.org.domain.enumeration.Direction;
import com.vegas.org.repository.AccountingJournalRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AccountingJournalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountingJournalResourceIT {

    private static final Direction DEFAULT_DIRECTION = Direction.CREDIT;
    private static final Direction UPDATED_DIRECTION = Direction.DEBIT;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_BALANCE_BEFORE = 1D;
    private static final Double UPDATED_BALANCE_BEFORE = 2D;

    private static final Double DEFAULT_BALANCE_AFTER = 1D;
    private static final Double UPDATED_BALANCE_AFTER = 2D;

    private static final ZonedDateTime DEFAULT_CREATE_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/accounting-journals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccountingJournalRepository accountingJournalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountingJournalMockMvc;

    private AccountingJournal accountingJournal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountingJournal createEntity(EntityManager em) {
        AccountingJournal accountingJournal = new AccountingJournal()
            .direction(DEFAULT_DIRECTION)
            .amount(DEFAULT_AMOUNT)
            .balanceBefore(DEFAULT_BALANCE_BEFORE)
            .balanceAfter(DEFAULT_BALANCE_AFTER)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT)
            .createBy(DEFAULT_CREATE_BY)
            .updateBy(DEFAULT_UPDATE_BY);
        return accountingJournal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountingJournal createUpdatedEntity(EntityManager em) {
        AccountingJournal accountingJournal = new AccountingJournal()
            .direction(UPDATED_DIRECTION)
            .amount(UPDATED_AMOUNT)
            .balanceBefore(UPDATED_BALANCE_BEFORE)
            .balanceAfter(UPDATED_BALANCE_AFTER)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT)
            .createBy(UPDATED_CREATE_BY)
            .updateBy(UPDATED_UPDATE_BY);
        return accountingJournal;
    }

    @BeforeEach
    public void initTest() {
        accountingJournal = createEntity(em);
    }

    @Test
    @Transactional
    void createAccountingJournal() throws Exception {
        int databaseSizeBeforeCreate = accountingJournalRepository.findAll().size();
        // Create the AccountingJournal
        restAccountingJournalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountingJournal))
            )
            .andExpect(status().isCreated());

        // Validate the AccountingJournal in the database
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeCreate + 1);
        AccountingJournal testAccountingJournal = accountingJournalList.get(accountingJournalList.size() - 1);
        assertThat(testAccountingJournal.getDirection()).isEqualTo(DEFAULT_DIRECTION);
        assertThat(testAccountingJournal.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAccountingJournal.getBalanceBefore()).isEqualTo(DEFAULT_BALANCE_BEFORE);
        assertThat(testAccountingJournal.getBalanceAfter()).isEqualTo(DEFAULT_BALANCE_AFTER);
        assertThat(testAccountingJournal.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testAccountingJournal.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
        assertThat(testAccountingJournal.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testAccountingJournal.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    void createAccountingJournalWithExistingId() throws Exception {
        // Create the AccountingJournal with an existing ID
        accountingJournal.setId(1L);

        int databaseSizeBeforeCreate = accountingJournalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountingJournalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountingJournal))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountingJournal in the database
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccountingJournals() throws Exception {
        // Initialize the database
        accountingJournalRepository.saveAndFlush(accountingJournal);

        // Get all the accountingJournalList
        restAccountingJournalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountingJournal.getId().intValue())))
            .andExpect(jsonPath("$.[*].direction").value(hasItem(DEFAULT_DIRECTION.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].balanceBefore").value(hasItem(DEFAULT_BALANCE_BEFORE.doubleValue())))
            .andExpect(jsonPath("$.[*].balanceAfter").value(hasItem(DEFAULT_BALANCE_AFTER.doubleValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(sameInstant(DEFAULT_CREATE_AT))))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(sameInstant(DEFAULT_UPDATE_AT))))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY)));
    }

    @Test
    @Transactional
    void getAccountingJournal() throws Exception {
        // Initialize the database
        accountingJournalRepository.saveAndFlush(accountingJournal);

        // Get the accountingJournal
        restAccountingJournalMockMvc
            .perform(get(ENTITY_API_URL_ID, accountingJournal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountingJournal.getId().intValue()))
            .andExpect(jsonPath("$.direction").value(DEFAULT_DIRECTION.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.balanceBefore").value(DEFAULT_BALANCE_BEFORE.doubleValue()))
            .andExpect(jsonPath("$.balanceAfter").value(DEFAULT_BALANCE_AFTER.doubleValue()))
            .andExpect(jsonPath("$.createAt").value(sameInstant(DEFAULT_CREATE_AT)))
            .andExpect(jsonPath("$.updateAt").value(sameInstant(DEFAULT_UPDATE_AT)))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY));
    }

    @Test
    @Transactional
    void getNonExistingAccountingJournal() throws Exception {
        // Get the accountingJournal
        restAccountingJournalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccountingJournal() throws Exception {
        // Initialize the database
        accountingJournalRepository.saveAndFlush(accountingJournal);

        int databaseSizeBeforeUpdate = accountingJournalRepository.findAll().size();

        // Update the accountingJournal
        AccountingJournal updatedAccountingJournal = accountingJournalRepository.findById(accountingJournal.getId()).get();
        // Disconnect from session so that the updates on updatedAccountingJournal are not directly saved in db
        em.detach(updatedAccountingJournal);
        updatedAccountingJournal
            .direction(UPDATED_DIRECTION)
            .amount(UPDATED_AMOUNT)
            .balanceBefore(UPDATED_BALANCE_BEFORE)
            .balanceAfter(UPDATED_BALANCE_AFTER)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT)
            .createBy(UPDATED_CREATE_BY)
            .updateBy(UPDATED_UPDATE_BY);

        restAccountingJournalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccountingJournal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccountingJournal))
            )
            .andExpect(status().isOk());

        // Validate the AccountingJournal in the database
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeUpdate);
        AccountingJournal testAccountingJournal = accountingJournalList.get(accountingJournalList.size() - 1);
        assertThat(testAccountingJournal.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testAccountingJournal.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAccountingJournal.getBalanceBefore()).isEqualTo(UPDATED_BALANCE_BEFORE);
        assertThat(testAccountingJournal.getBalanceAfter()).isEqualTo(UPDATED_BALANCE_AFTER);
        assertThat(testAccountingJournal.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testAccountingJournal.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
        assertThat(testAccountingJournal.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testAccountingJournal.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    void putNonExistingAccountingJournal() throws Exception {
        int databaseSizeBeforeUpdate = accountingJournalRepository.findAll().size();
        accountingJournal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountingJournalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountingJournal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountingJournal))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountingJournal in the database
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccountingJournal() throws Exception {
        int databaseSizeBeforeUpdate = accountingJournalRepository.findAll().size();
        accountingJournal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountingJournalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountingJournal))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountingJournal in the database
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccountingJournal() throws Exception {
        int databaseSizeBeforeUpdate = accountingJournalRepository.findAll().size();
        accountingJournal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountingJournalMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountingJournal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountingJournal in the database
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountingJournalWithPatch() throws Exception {
        // Initialize the database
        accountingJournalRepository.saveAndFlush(accountingJournal);

        int databaseSizeBeforeUpdate = accountingJournalRepository.findAll().size();

        // Update the accountingJournal using partial update
        AccountingJournal partialUpdatedAccountingJournal = new AccountingJournal();
        partialUpdatedAccountingJournal.setId(accountingJournal.getId());

        partialUpdatedAccountingJournal.direction(UPDATED_DIRECTION).balanceBefore(UPDATED_BALANCE_BEFORE).createAt(UPDATED_CREATE_AT);

        restAccountingJournalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountingJournal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountingJournal))
            )
            .andExpect(status().isOk());

        // Validate the AccountingJournal in the database
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeUpdate);
        AccountingJournal testAccountingJournal = accountingJournalList.get(accountingJournalList.size() - 1);
        assertThat(testAccountingJournal.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testAccountingJournal.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAccountingJournal.getBalanceBefore()).isEqualTo(UPDATED_BALANCE_BEFORE);
        assertThat(testAccountingJournal.getBalanceAfter()).isEqualTo(DEFAULT_BALANCE_AFTER);
        assertThat(testAccountingJournal.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testAccountingJournal.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
        assertThat(testAccountingJournal.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testAccountingJournal.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    void fullUpdateAccountingJournalWithPatch() throws Exception {
        // Initialize the database
        accountingJournalRepository.saveAndFlush(accountingJournal);

        int databaseSizeBeforeUpdate = accountingJournalRepository.findAll().size();

        // Update the accountingJournal using partial update
        AccountingJournal partialUpdatedAccountingJournal = new AccountingJournal();
        partialUpdatedAccountingJournal.setId(accountingJournal.getId());

        partialUpdatedAccountingJournal
            .direction(UPDATED_DIRECTION)
            .amount(UPDATED_AMOUNT)
            .balanceBefore(UPDATED_BALANCE_BEFORE)
            .balanceAfter(UPDATED_BALANCE_AFTER)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT)
            .createBy(UPDATED_CREATE_BY)
            .updateBy(UPDATED_UPDATE_BY);

        restAccountingJournalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountingJournal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountingJournal))
            )
            .andExpect(status().isOk());

        // Validate the AccountingJournal in the database
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeUpdate);
        AccountingJournal testAccountingJournal = accountingJournalList.get(accountingJournalList.size() - 1);
        assertThat(testAccountingJournal.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testAccountingJournal.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAccountingJournal.getBalanceBefore()).isEqualTo(UPDATED_BALANCE_BEFORE);
        assertThat(testAccountingJournal.getBalanceAfter()).isEqualTo(UPDATED_BALANCE_AFTER);
        assertThat(testAccountingJournal.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testAccountingJournal.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
        assertThat(testAccountingJournal.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testAccountingJournal.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAccountingJournal() throws Exception {
        int databaseSizeBeforeUpdate = accountingJournalRepository.findAll().size();
        accountingJournal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountingJournalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountingJournal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountingJournal))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountingJournal in the database
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccountingJournal() throws Exception {
        int databaseSizeBeforeUpdate = accountingJournalRepository.findAll().size();
        accountingJournal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountingJournalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountingJournal))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountingJournal in the database
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccountingJournal() throws Exception {
        int databaseSizeBeforeUpdate = accountingJournalRepository.findAll().size();
        accountingJournal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountingJournalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountingJournal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountingJournal in the database
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccountingJournal() throws Exception {
        // Initialize the database
        accountingJournalRepository.saveAndFlush(accountingJournal);

        int databaseSizeBeforeDelete = accountingJournalRepository.findAll().size();

        // Delete the accountingJournal
        restAccountingJournalMockMvc
            .perform(delete(ENTITY_API_URL_ID, accountingJournal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountingJournal> accountingJournalList = accountingJournalRepository.findAll();
        assertThat(accountingJournalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
