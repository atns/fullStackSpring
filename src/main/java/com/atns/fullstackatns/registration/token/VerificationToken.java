package com.atns.fullstackatns.registration.token;

import com.atns.fullstackatns.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expirationTime = TokenExpirationTime.getExpirationTime();
    }
}
