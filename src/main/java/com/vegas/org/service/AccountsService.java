package com.vegas.org.service;

import com.vegas.org.domain.Accounts;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Accounts}.
 */
public interface AccountsService {
    /**
     * Save a accounts.
     *
     * @param accounts the entity to save.
     * @return the persisted entity.
     */
    Accounts save(Accounts accounts);

    /**
     * Updates a accounts.
     *
     * @param accounts the entity to update.
     * @return the persisted entity.
     */
    Accounts update(Accounts accounts);

    /**
     * Partially updates a accounts.
     *
     * @param accounts the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Accounts> partialUpdate(Accounts accounts);

    /**
     * Get all the accounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Accounts> findAll(Pageable pageable);

    /**
     * Get the "id" accounts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Accounts> findOne(Long id);

    /**
     * Delete the "id" accounts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
