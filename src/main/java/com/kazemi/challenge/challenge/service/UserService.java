package com.kazemi.challenge.challenge.service;

import com.kazemi.challenge.challenge.DtoMapper;
import com.kazemi.challenge.challenge.dto.*;
import com.kazemi.challenge.challenge.exception.BusinessException;
import com.kazemi.challenge.challenge.exception.BusinessExceptionType;
import com.kazemi.challenge.challenge.model.Email;
import com.kazemi.challenge.challenge.model.PhoneNumber;
import com.kazemi.challenge.challenge.model.User;
import com.kazemi.challenge.challenge.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements CrudService<User, Long> {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PhoneNumberService phoneNumberService;
    private final DtoMapper dtoMapper;

    @Override
    public Set<User> findAll() {
        Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserResponseDto findUserById(Long id) {
        return dtoMapper.userDto(userRepository.findById(id).orElse(null));
    }

    public List<UserResponseDto> findByName(String name) {
        List<User> users = userRepository.findByName(name);
        return dtoMapper.userDtos(users);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(u -> userRepository.deleteById(id));
    }

    @Transactional
    public UserResponseDto createNewUser(UserRequestDto request) {

        if (request == null ||
                request.getFirstName() == null ||
                request.getLastName() == null) {
            log.error("input data is not valid.");
            throw new BusinessException(BusinessExceptionType.INVALID_DATA);
        }

        UserResponseDto userDto = UserResponseDto.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .build();

        User savedUser = userRepository.save(dtoMapper.user(userDto));
        saveContact(request, savedUser);
        UserResponseDto result = dtoMapper.userDto(savedUser);
        result.setEmails(request.getEmails());
        result.setPhoneNumbers(request.getPhoneNumbers());
        return result;

    }

    @Transactional
    public void addContactData(UserRequestDto request) {

        if (request == null ||
                request.getId() == null ||
                (CollectionUtils.isEmpty(request.getEmails()) &&
                        CollectionUtils.isEmpty(request.getPhoneNumbers()))) {
            log.error("input data is not valid.");
            throw new BusinessException(BusinessExceptionType.INVALID_DATA);
        }

        User user = findById(request.getId());
        saveContact(request, user);

    }

    @Transactional
    public void updateContactData(UserContactUpdateRequestDto request) {

        if (request == null ||
                request.getId() == null ||
                (CollectionUtils.isEmpty(request.getEmails()) &&
                        CollectionUtils.isEmpty(request.getPhoneNumbers()))) {
            log.error("input data is not valid.");
            throw new BusinessException(BusinessExceptionType.INVALID_DATA);
        }

        User user = findById(request.getId());
        updateContact(request, user);

    }

    public void saveContact(UserRequestDto request, User user) {

        Set<String> mails = dtoMapper.mails(request.getEmails()).stream()
                .map(Email::getMail)
                .collect(Collectors.toSet());

        Set<String> phoneNumbers = dtoMapper.phoneNumbers(request.getPhoneNumbers()).stream()
                .map(PhoneNumber::getNumber)
                .collect(Collectors.toSet());

        emailService.saveNewMail(mails, user);
        phoneNumberService.saveNewPhoneNumber(phoneNumbers, user);

    }

    @Transactional
    public void updateContact(UserContactUpdateRequestDto request, User user) {

        if (!CollectionUtils.isEmpty(request.getEmails())) {
            emailService.updateEmails(request.getEmails(), user);
        }

        if (!CollectionUtils.isEmpty(request.getPhoneNumbers())) {
            phoneNumberService.updatePhoneNumbers(request.getPhoneNumbers(), user);
        }
    }
}
