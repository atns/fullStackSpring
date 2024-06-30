package com.atns.fullstackatns.registration.password;

import com.atns.fullstackatns.registration.token.TokenExpirationTime;
import com.atns.fullstackatns.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.expirationTime = TokenExpirationTime.getExpirationTime();
        this.user = user;
    }
}
