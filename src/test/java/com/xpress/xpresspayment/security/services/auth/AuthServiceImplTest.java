package com.xpress.xpresspayment.security.services.auth;

import com.xpress.xpresspayment.data.dtos.requests.LoginRequest;
import com.xpress.xpresspayment.data.dtos.requests.RegistrationRequest;
import com.xpress.xpresspayment.data.dtos.responses.LoginResponse;
import com.xpress.xpresspayment.exceptions.XpressException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {

    @Autowired
    private AuthService authService;

    private LoginRequest loginRequest;



    @BeforeEach
    void setUp( ){

        loginRequest = LoginRequest.builder()
                .email("olamide@gmail.com")
                .password("P@$$word123")
                .build();



    }

    @Test
    void login() throws XpressException {
        LoginResponse loginResponse = authService.login(loginRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getUserId()).isGreaterThan(0);
        assertThat(loginResponse.getMessage()).isNotNull();
        assertThat(loginResponse.getJwtToken()).isNotNull();
    }

    @Test
    void logout() {
    }
}