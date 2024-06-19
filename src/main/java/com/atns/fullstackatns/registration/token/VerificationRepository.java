package com.atns.fullstackatns.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<VerificationToken, Long> {


    Optional<VerificationToken> findByToken(String token);




}
