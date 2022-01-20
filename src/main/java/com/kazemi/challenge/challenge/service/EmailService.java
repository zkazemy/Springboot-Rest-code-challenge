package com.kazemi.challenge.challenge.service;

import com.kazemi.challenge.challenge.dto.UserUpdateEmailRequestDto;
import com.kazemi.challenge.challenge.model.Email;
import com.kazemi.challenge.challenge.model.User;
import com.kazemi.challenge.challenge.repository.EmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class EmailService implements CrudService<Email, Long> {
    private final EmailRepository emailRepository;

    @Override
    public Set<Email> findAll() {
        Set<Email> emails = new HashSet<>();
        emailRepository.findAll().forEach(emails::add);
        return emails;
    }

    @Override
    public Email findById(Long id) {
        return emailRepository.findById(id).orElse(null);
    }

    @Override
    public Email save(Email email) {
        return emailRepository.save(email);
    }

    @Override
    public void delete(Email email) {
        emailRepository.delete(email);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Email> email = emailRepository.findById(id);
        email.ifPresent(u -> emailRepository.deleteById(id));
    }

    public void updateEmail(UserUpdateEmailRequestDto request, User user) {
        Optional<Email> existingEmail = findByMail(request.getOldEmail(), user);
        existingEmail.ifPresent(e -> {
            existingEmail.get().setMail(request.getNewEmail());
            emailRepository.save(existingEmail.get());
        });
    }

    public void updateEmails(List<UserUpdateEmailRequestDto> mails, User user) {
        mails.forEach(emailRequest -> updateEmail(emailRequest, user));
    }

    public Optional<Email> findByMail(String email, User user) {
        return emailRepository.findFirstByMailAndUser(email, user);
    }

    public boolean checkDuplicateEmail(String email, User user) {
        Optional<Email> existingEmail = emailRepository.findFirstByMailAndUser(email, user);
        return existingEmail.isPresent();
    }

    public void saveNewMail(Set<String> mails, User user) {
        mails.forEach(email -> {
            if (!checkDuplicateEmail(email, user)) {
                Email newEmail = new Email();
                newEmail.setUser(user);
                newEmail.setMail(email);
                emailRepository.save(newEmail);
            }
        });
    }
}
