package com.registration.repository;

import com.registration.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for User
 * @author mohamed.rihan
 */
public interface UserJpaRepository extends JpaRepository<User, Long> {

    /*
     * Method for find user by email
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);
}