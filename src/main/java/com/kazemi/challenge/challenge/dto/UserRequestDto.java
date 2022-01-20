package com.kazemi.challenge.challenge.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserRequestDto {

    private Long id;

    private String firstName;

    private String lastName;

    private Set<EmailDto> emails = new HashSet<>();

    private Set<PhoneNumberDto> phoneNumbers = new HashSet<>();
}
