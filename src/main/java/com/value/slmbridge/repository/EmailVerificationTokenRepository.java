package com.value.slmbridge.repository;

import com.value.slmbridge.entity.EmailVerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends MongoRepository<EmailVerificationToken, String> {

    Optional<EmailVerificationToken> findByToken(String token);

    void deleteByEmail(String email);
}