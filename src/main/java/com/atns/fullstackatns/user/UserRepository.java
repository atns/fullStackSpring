package com.atns.fullstackatns.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findAllByIsEnabled(boolean b);

    Optional<User> findById(Long id);

    @Modifying
    @Query(value = "UPDATE User  u  set u.firstName =:firstName, u.lastName=:lastName, u.email=:email WHERE u.id=:id")
    void updateUser(String firstName, String lastName,String email,  Long id);


}
