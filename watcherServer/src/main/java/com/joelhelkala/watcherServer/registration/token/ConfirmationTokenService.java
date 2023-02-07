package com.joelhelkala.watcherServer.registration.token;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    public void deleteTokenByUser(Long userId) {
        log.info("Deleting token with userID : {}", userId);
        Optional<ConfirmationToken> exists = confirmationTokenRepository.existsByUserId(userId);
        if(!exists.isPresent()) return;
        confirmationTokenRepository.deleteByUserId(userId);
    }
}
