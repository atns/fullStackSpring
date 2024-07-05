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

        //System.out.println("token: "+token);

//        System.out.println("θα αναζητησει το "+token);///πρεπει να ειναι ο κωδικος του τοκεν και οχι το αντικειμενο
        Optional<VerificationToken> theToken = verificationRepository.findByToken(token);

        System.out.println(" Optional<VerificationToken> theToken = "+theToken);

        if (theToken.isEmpty()) {
            return "invalid";
        }
        User user = theToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if (theToken.get().getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            return "expired";
        }

        //valid:

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
        System.out.println("finding verification token...... "+token);
        System.out.println("####"+verificationRepository.findByToken(token));
        System.out.println("####"+verificationRepository.findByToken(token).get().getId());

        System.out.println();
        return verificationRepository.findByToken(token);
    }

    @Override
    public void deleteUserToken(Long id) {
        System.out.println("deleting verification token...... "+id);
        verificationRepository.deleteByUserId(id);
    }
}
