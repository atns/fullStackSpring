package com.atns.fullstackatns.user;


import com.atns.fullstackatns.registration.RegistrationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User registerUser(RegistrationRequest registrationRequest) {
        return null;
    }

    @Override
    public Optional<User> findUserbyEmail(String email) {
        return Optional.empty();
    }
}
