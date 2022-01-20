package com.kazemi.challenge.challenge.repository;

import com.kazemi.challenge.challenge.model.PhoneNumber;
import com.kazemi.challenge.challenge.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Long> {
    Optional<PhoneNumber> findFirstByNumberAndUser(String phoneNumber, User user);
}
