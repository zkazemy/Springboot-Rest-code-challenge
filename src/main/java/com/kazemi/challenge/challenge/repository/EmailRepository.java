package com.kazemi.challenge.challenge.repository;

import com.kazemi.challenge.challenge.model.Email;
import com.kazemi.challenge.challenge.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailRepository extends CrudRepository<Email, Long> {
    Optional<Email> findFirstByMailAndUser(String mail, User user);
}
