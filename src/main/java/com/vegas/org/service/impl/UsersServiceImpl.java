package com.vegas.org.service.impl;

import com.vegas.org.domain.Users;
import com.vegas.org.repository.UsersRepository;
import com.vegas.org.service.UsersService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Users}.
 */
@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users save(Users users) {
        log.debug("Request to save Users : {}", users);
        return usersRepository.save(users);
    }

    @Override
    public Users update(Users users) {
        log.debug("Request to update Users : {}", users);
        return usersRepository.save(users);
    }

    @Override
    public Optional<Users> partialUpdate(Users users) {
        log.debug("Request to partially update Users : {}", users);

        return usersRepository
            .findById(users.getId())
            .map(existingUsers -> {
                if (users.getName() != null) {
                    existingUsers.setName(users.getName());
                }
                if (users.getEmail() != null) {
                    existingUsers.setEmail(users.getEmail());
                }
                if (users.getPassword() != null) {
                    existingUsers.setPassword(users.getPassword());
                }
                if (users.getCreateAt() != null) {
                    existingUsers.setCreateAt(users.getCreateAt());
                }
                if (users.getUpdateAt() != null) {
                    existingUsers.setUpdateAt(users.getUpdateAt());
                }
                if (users.getCreateBy() != null) {
                    existingUsers.setCreateBy(users.getCreateBy());
                }
                if (users.getUpdateBy() != null) {
                    existingUsers.setUpdateBy(users.getUpdateBy());
                }

                return existingUsers;
            })
            .map(usersRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Users> findAll(Pageable pageable) {
        log.debug("Request to get all Users");
        return usersRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Users> findOne(Long id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Users : {}", id);
        usersRepository.deleteById(id);
    }
}
