package com.kazemi.challenge.challenge.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserUpdatePhoneNumberRequestDto {
    String oldPhoneNumber;
    String newPhoneNumber;
}
