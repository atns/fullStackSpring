package com.atns.fullstackatns.registration.token;

import com.atns.fullstackatns.user.User;

import java.util.Date;
import java.util.Optional;

public interface IVerificationTokenService {

    String validateToken(String token);

    void saveVerificationTokenForUser(User user, String token);

    Optional<VerificationToken> findbyToken(String token);


    void deleteUserToken(Long id);
}
