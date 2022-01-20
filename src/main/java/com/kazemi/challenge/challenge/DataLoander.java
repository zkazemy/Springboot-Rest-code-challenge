package com.kazemi.challenge.challenge;

import com.kazemi.challenge.challenge.model.User;
import com.kazemi.challenge.challenge.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoander implements CommandLineRunner {

    private final UserService userService;
    @Override
    public void run(String... args) throws Exception {
        User  user = User.builder()
                .firstName("reza")
                .lastName("akbari")
                .build();

        userService.save(user);

        User  user2 = User.builder()
                .firstName("reza")
                .lastName("morasli")
                .build();

        userService.save(user2);

        User  user3 = User.builder()
                .firstName("kobra")
                .lastName("albare")
                .build();

        userService.save(user3);

    }
}
