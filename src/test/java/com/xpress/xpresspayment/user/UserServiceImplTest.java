package com.xpress.xpresspayment.user;

import com.xpress.xpresspayment.data.dtos.requests.RegistrationRequest;
import com.xpress.xpresspayment.data.dtos.responses.RegistrationResponse;
import com.xpress.xpresspayment.exceptions.XpressException;
import com.xpress.xpresspayment.models.AppUser;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest

class UserServiceImplTest {
    @Autowired
    private UserService userService;

    private RegistrationRequest registrationRequest;


    @BeforeEach
    void  setUp(){
        registrationRequest = RegistrationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .password("P@$$word123")
                .confirmPassword("P@$$word123")
                .email("olamide@gmail.com")
                .phoneNumber("09078453627")
                .build();

    }

    @Test
    void testThatUserCanRegister() throws XpressException {
        RegistrationResponse registrationResponse = userService.register(registrationRequest);
        assertThat(registrationResponse).isNotNull();
        assertThat(registrationResponse.getMessage()).isNotNull();



    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void existByEmail() {
    }

    @Test
    void findAllDisabledUsers() {
    }

    @Test
    void disableUser() {
    }

    @Test
    void registerFailedLogin() {
    }
}