package com.vegas.org.service;

import com.vegas.org.domain.Users;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Users}.
 */
public interface UsersService {
    /**
     * Save a users.
     *
     * @param users the entity to save.
     * @return the persisted entity.
     */
    Users save(Users users);

    /**
     * Updates a users.
     *
     * @param users the entity to update.
     * @return the persisted entity.
     */
    Users update(Users users);

    /**
     * Partially updates a users.
     *
     * @param users the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Users> partialUpdate(Users users);

    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Users> findAll(Pageable pageable);

    /**
     * Get the "id" users.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Users> findOne(Long id);

    /**
     * Delete the "id" users.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
