package com.atns.fullstackatns.registration.token;

import com.atns.fullstackatns.user.User;
import com.atns.fullstackatns.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService implements IVerificationTokenService {


    private final VerificationRepository verificationRepository;

    private final UserRepository userRepository;


    @Override
    public String validateToken(String token) {

        Optional<VerificationToken> theToken = verificationRepository.findByToken(token);

        if (theToken.isEmpty()) {
            return "Invalid verification token";
        }
        User user = theToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if (theToken.get().getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            return "Expired token";
        }
        user.setEnabled(true);

        userRepository.save(user);


        return "valid";
    }

    @Override
    public void saveVerificationTokenForUser(User user, String token) {

        var verificationToken = new VerificationToken(user, token);
        verificationRepository.save(verificationToken);

    }

    @Override
    public Optional<VerificationToken> findbyToken(String token) {
        return verificationRepository.findByToken(token);
    }
}
