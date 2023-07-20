package com.xpress.xpresspayment.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean enabled;
    private boolean isVerified;
    private String passwordResetToken;
    private String loginToken;
    private int failedLoginAttempt;
    private LocalDateTime timeLocked;
}
