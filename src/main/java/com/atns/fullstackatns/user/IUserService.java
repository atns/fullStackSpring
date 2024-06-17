package com.atns.fullstackatns.user;

import com.atns.fullstackatns.registration.RegistrationRequest;


import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();
    User registerUser(RegistrationRequest registrationRequest);
    Optional<User> findUserbyEmail(String email);

}