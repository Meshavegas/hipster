package com.vegas.org.web.rest;

import com.vegas.org.domain.AccountingJournal;
import com.vegas.org.repository.AccountingJournalRepository;
import com.vegas.org.service.AccountingJournalService;
import com.vegas.org.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.vegas.org.domain.AccountingJournal}.
 */
@RestController
@RequestMapping("/api")
public class AccountingJournalResource {

    private final Logger log = LoggerFactory.getLogger(AccountingJournalResource.class);

    private static final String ENTITY_NAME = "accountingJournal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountingJournalService accountingJournalService;

    private final AccountingJournalRepository accountingJournalRepository;

    public AccountingJournalResource(
        AccountingJournalService accountingJournalService,
        AccountingJournalRepository accountingJournalRepository
    ) {
        this.accountingJournalService = accountingJournalService;
        this.accountingJournalRepository = accountingJournalRepository;
    }

    /**
     * {@code POST  /accounting-journals} : Create a new accountingJournal.
     *
     * @param accountingJournal the accountingJournal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountingJournal, or with status {@code 400 (Bad Request)} if the accountingJournal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accounting-journals")
    public ResponseEntity<AccountingJournal> createAccountingJournal(@RequestBody AccountingJournal accountingJournal)
        throws URISyntaxException {
        log.debug("REST request to save AccountingJournal : {}", accountingJournal);
        if (accountingJournal.getId() != null) {
            throw new BadRequestAlertException("A new accountingJournal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountingJournal result = accountingJournalService.save(accountingJournal);
        return ResponseEntity
            .created(new URI("/api/accounting-journals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accounting-journals/:id} : Updates an existing accountingJournal.
     *
     * @param id the id of the accountingJournal to save.
     * @param accountingJournal the accountingJournal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountingJournal,
     * or with status {@code 400 (Bad Request)} if the accountingJournal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountingJournal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accounting-journals/{id}")
    public ResponseEntity<AccountingJournal> updateAccountingJournal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountingJournal accountingJournal
    ) throws URISyntaxException {
        log.debug("REST request to update AccountingJournal : {}, {}", id, accountingJournal);
        if (accountingJournal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountingJournal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountingJournalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccountingJournal result = accountingJournalService.update(accountingJournal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountingJournal.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /accounting-journals/:id} : Partial updates given fields of an existing accountingJournal, field will ignore if it is null
     *
     * @param id the id of the accountingJournal to save.
     * @param accountingJournal the accountingJournal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountingJournal,
     * or with status {@code 400 (Bad Request)} if the accountingJournal is not valid,
     * or with status {@code 404 (Not Found)} if the accountingJournal is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountingJournal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/accounting-journals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountingJournal> partialUpdateAccountingJournal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountingJournal accountingJournal
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccountingJournal partially : {}, {}", id, accountingJournal);
        if (accountingJournal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountingJournal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountingJournalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountingJournal> result = accountingJournalService.partialUpdate(accountingJournal);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountingJournal.getId().toString())
        );
    }

    /**
     * {@code GET  /accounting-journals} : get all the accountingJournals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountingJournals in body.
     */
    @GetMapping("/accounting-journals")
    public ResponseEntity<List<AccountingJournal>> getAllAccountingJournals(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AccountingJournals");
        Page<AccountingJournal> page = accountingJournalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accounting-journals/:id} : get the "id" accountingJournal.
     *
     * @param id the id of the accountingJournal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountingJournal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accounting-journals/{id}")
    public ResponseEntity<AccountingJournal> getAccountingJournal(@PathVariable Long id) {
        log.debug("REST request to get AccountingJournal : {}", id);
        Optional<AccountingJournal> accountingJournal = accountingJournalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountingJournal);
    }

    /**
     * {@code DELETE  /accounting-journals/:id} : delete the "id" accountingJournal.
     *
     * @param id the id of the accountingJournal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accounting-journals/{id}")
    public ResponseEntity<Void> deleteAccountingJournal(@PathVariable Long id) {
        log.debug("REST request to delete AccountingJournal : {}", id);
        accountingJournalService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
