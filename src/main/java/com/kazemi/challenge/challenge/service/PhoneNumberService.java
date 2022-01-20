package com.kazemi.challenge.challenge.service;

import com.kazemi.challenge.challenge.dto.UserUpdatePhoneNumberRequestDto;
import com.kazemi.challenge.challenge.model.PhoneNumber;
import com.kazemi.challenge.challenge.model.User;
import com.kazemi.challenge.challenge.repository.PhoneNumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PhoneNumberService implements CrudService<PhoneNumber, Long> {
    private final PhoneNumberRepository phoneNumberRepository;

    @Override
    public Set<PhoneNumber> findAll() {
        Set<PhoneNumber> phoneNumbers = new HashSet<>();
        phoneNumberRepository.findAll().forEach(phoneNumbers::add);
        return phoneNumbers;
    }

    @Override
    public PhoneNumber findById(Long id) {
        return phoneNumberRepository.findById(id).orElse(null);

    }

    @Override
    public PhoneNumber save(PhoneNumber phoneNumber) {
        return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public void delete(PhoneNumber phoneNumber) {
        phoneNumberRepository.delete(phoneNumber);
    }

    @Override
    public void deleteById(Long id) {
        Optional<PhoneNumber> phoneNumber = phoneNumberRepository.findById(id);
        phoneNumber.ifPresent(u -> phoneNumberRepository.deleteById(id));
    }

    public Optional<PhoneNumber> findByPhoneNumber(String phoneNumber, User user) {
        return phoneNumberRepository.findFirstByNumberAndUser(phoneNumber, user);
    }

    public void updatePhoneNumber(UserUpdatePhoneNumberRequestDto request, User user) {
        Optional<PhoneNumber> existingPhoneNumber = findByPhoneNumber(request.getOldPhoneNumber(), user);
        existingPhoneNumber.ifPresent(e -> {
            existingPhoneNumber.get().setNumber(request.getNewPhoneNumber());
            phoneNumberRepository.save(existingPhoneNumber.get());
        });
    }

    public void updatePhoneNumbers(List<UserUpdatePhoneNumberRequestDto> phones, User user) {
        phones.forEach(phoneRequest -> updatePhoneNumber(phoneRequest, user));
    }

    public boolean checkDuplicatePhoneNumber(String phoneNumber, User user) {
        Optional<PhoneNumber> existingEmail = phoneNumberRepository.findFirstByNumberAndUser(phoneNumber, user);
        return existingEmail.isPresent();
    }

    public void saveNewPhoneNumber(Set<String> phoneNumbers, User user) {
        phoneNumbers.forEach(phoneNumber -> {
            if (!checkDuplicatePhoneNumber(phoneNumber, user)) {
                PhoneNumber newPhoneNumber = new PhoneNumber();
                newPhoneNumber.setUser(user);
                newPhoneNumber.setNumber(phoneNumber);
                phoneNumberRepository.save(newPhoneNumber);
            }
        });
    }

}
