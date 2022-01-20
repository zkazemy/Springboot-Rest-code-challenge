package com.kazemi.challenge.challenge.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserContactAddRequestDto {

    private Long id;

    private Set<EmailDto> emails = new HashSet<>();

    private Set<PhoneNumberDto> phoneNumbers = new HashSet<>();
    
}
