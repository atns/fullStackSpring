package com.atns.fullstackatns.user;

import com.atns.fullstackatns.registration.RegistrationRequest;


import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();
    List<User> getAllEnabledUsers();  // Add this line

    User registerUser(RegistrationRequest registrationRequest);
    User findUserbyEmail(String email);



}
