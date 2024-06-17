package com.atns.fullstackatns.registration;

import com.atns.fullstackatns.user.Role;
import lombok.Data;

import java.util.List;


@Data
public class RegistrationRequest {

private String firstName;

private String lastName;

private String email;
private String password;
private List<Role> roles;

}
