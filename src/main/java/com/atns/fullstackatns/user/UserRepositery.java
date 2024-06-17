package com.atns.fullstackatns.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositery extends JpaRepository<User, Long> {
}
