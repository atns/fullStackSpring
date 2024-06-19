package com.atns.fullstackatns.user;


import com.atns.fullstackatns.registration.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService implements IUserService {


    //Don't need Autowired // @RequiredArgsConstructor(onConstructor_ = {@Autowired})
    private final UserRepository _userRepository;

    private final PasswordEncoder _passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return _userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest registration) {
        var user = new User(registration.getFirstName(),
                registration.getLastName(),
                registration.getEmail(),
                _passwordEncoder.encode(registration.getPassword()) ,
                Arrays.asList(new Role("ROLE_USER")));
        return  _userRepository.save(user);
    }

    @Override
    public User findUserbyEmail(String email) {
        return _userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }


}
