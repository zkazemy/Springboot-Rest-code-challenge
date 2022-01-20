package com.kazemi.challenge.challenge.dto;

import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserContactUpdateRequestDto {

    private Long id;

    private List<UserUpdateEmailRequestDto> emails = new ArrayList<>();

    private List<UserUpdatePhoneNumberRequestDto> phoneNumbers = new ArrayList<>();
}
