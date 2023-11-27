package com.monoholy.repository;

import com.monoholy.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link User} entities. Extends {@link CrudRepository} to provide CRUD
 * operations for User entities.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * Retrieves a User entity based on a given encrypted username.
     *
     * @param encryptedUsername The encrypted username used to find the corresponding User entity.
     * @return An Optional containing the User entity if found, or an empty Optional if no entity
     *     matches the given username.
     */
    Optional<User> findByUsername(String encryptedUsername);

    /**
     * Retrieves a User entity based on a given email address.
     *
     * @param email The email address used to find the corresponding User entity.
     * @return An Optional containing the User entity if found, or an empty Optional if no entity
     *     matches the given email.
     */
    Optional<User> findByEmail(String email);
}
