package com.kazemi.challenge.challenge;

import com.kazemi.challenge.challenge.dto.EmailDto;
import com.kazemi.challenge.challenge.dto.PhoneNumberDto;
import com.kazemi.challenge.challenge.dto.UserResponseDto;
import com.kazemi.challenge.challenge.model.Email;
import com.kazemi.challenge.challenge.model.PhoneNumber;
import com.kazemi.challenge.challenge.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
@Component
public abstract class DtoMapper {

    public List<UserResponseDto> userDtos(List<User> userList) {

        if (CollectionUtils.isEmpty(userList))
            return null;

        List<UserResponseDto> userDtos = new ArrayList<>();
        userList.stream().forEach(e ->
                userDtos.add(UserResponseDto.builder()
                        .id(e.getId())
                        .firstName(e.getFirstName())
                        .lastName(e.getLastName())
                        .phoneNumbers(phoneNumberDtos(e.getPhoneNumbers()))
                        .emails(mailDtos(e.getEmails()))
                        .build())
        );

        return userDtos;
    }

    public UserResponseDto userDto(User user) {

        if(user == null)
            return  null;

        return UserResponseDto
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .emails(mailDtos(user.getEmails()))
                .phoneNumbers(phoneNumberDtos(user.getPhoneNumbers()))
                .build();
    }

    public User user(UserResponseDto userDto) {

        if(userDto == null)
            return  null;

        return User
                .builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .emails(mails(userDto.getEmails()))
                .phoneNumbers(phoneNumbers(userDto.getPhoneNumbers()))
                .build();
    }

    public Set<EmailDto> mailDtos(Set<Email> emailList) {

        if (CollectionUtils.isEmpty(emailList))
            return null;

        Set<EmailDto> emailDtos = new HashSet<>();
        emailList.stream().forEach(e ->
                emailDtos.add(EmailDto.builder()
                        .mail(e.getMail())
                        .build())
        );

        return emailDtos;
    }

    public Set<PhoneNumberDto> phoneNumberDtos(Set<PhoneNumber> phoneNumberList) {

        if (CollectionUtils.isEmpty(phoneNumberList))
            return null;

        Set<PhoneNumberDto> phoneNumbersDto = new HashSet<>();
        phoneNumberList.stream().forEach(ph ->
                phoneNumbersDto.add(PhoneNumberDto.builder()
                        .number(ph.getNumber())
                        .build())
        );

        return phoneNumbersDto;
    }

    public Set<Email> mails(Set<EmailDto> emailDtoList) {

        if (CollectionUtils.isEmpty(emailDtoList))
            return null;

        Set<Email> emails = new HashSet<>();
        emailDtoList.stream().forEach(e ->
                emails.add(Email.builder()
                        .mail(e.getMail())
                        .build())
        );

        return emails;
    }

    public Set<PhoneNumber> phoneNumbers(Set<PhoneNumberDto> phoneNumberDtoList) {

        if (CollectionUtils.isEmpty(phoneNumberDtoList))
            return null;

        Set<PhoneNumber> phoneNumbers = new HashSet<>();
        phoneNumberDtoList.stream().forEach(ph ->
                phoneNumbers.add(PhoneNumber.builder()
                        .number(ph.getNumber())
                        .build())
        );

        return phoneNumbers;
    }
}
