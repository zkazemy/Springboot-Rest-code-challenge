package com.kazemi.challenge.challenge.repository;

import com.kazemi.challenge.challenge.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from User u  where u.firstName = :name or u.lastName = :name")
    List<User> findByName(String name);

}
