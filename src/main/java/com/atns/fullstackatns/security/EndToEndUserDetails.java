package com.atns.fullstackatns.security;


import com.atns.fullstackatns.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class EndToEndUserDetails implements UserDetails {


    private String username;

    private String password;

    private boolean isEanbled;

    public List<GrantedAuthority> authorities;


    public EndToEndUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.isEanbled = user.isEnabled();
        this.authorities = Arrays.stream(user.getRole().toString().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEanbled;
    }
}
