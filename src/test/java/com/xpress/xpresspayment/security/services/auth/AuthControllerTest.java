package com.xpress.xpresspayment.security.services.auth;

import com.xpress.xpresspayment.controller.AuthController;
import com.xpress.xpresspayment.data.dtos.requests.LoginRequest;
import com.xpress.xpresspayment.data.dtos.responses.LoginResponse;
import com.xpress.xpresspayment.exceptions.XpressException;
import com.xpress.xpresspayment.models.AppUser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testLoginSuccessful() throws XpressException {

        String email = "test@example.com";
        String password = "testpassword";
        LoginRequest loginRequest = new LoginRequest(email, password);


        AppUser appUser = new AppUser();
        appUser.setId(1L);
        when(authService.login(loginRequest)).thenReturn(LoginResponse.builder()

                .email(loginRequest.getEmail()).build());


        ResponseEntity<LoginResponse> responseEntity = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(appUser.getId(), responseEntity.getBody().getUserId());

        verify(authService, times(1)).login(loginRequest);
    }

    @Test
    public void testLogin_InvalidCredentials() throws XpressException {

        String email = "test@example.com";
        String password = "wrongpassword";
        LoginRequest loginRequest = new LoginRequest(email, password);


        when(authService.login(loginRequest)).thenThrow(new XpressException("Invalid username or password"));

        assertThrows(XpressException.class, () -> authController.login(loginRequest));

        verify(authService, times(1)).login(loginRequest);
    }

    @Test
    public void testLogin_AccountLocked() throws XpressException {

        String email = "test@example.com";
        String password = "wrongpassword";
        LoginRequest loginRequest = new LoginRequest(email, password);
        AppUser appUser = new AppUser();
        appUser.setFailedLoginAttempt(2);

        when(authService.login(loginRequest)).thenThrow(new XpressException("Invalid username or password"));

        assertThrows(XpressException.class, () -> authController.login(loginRequest));

        verify(authService, times(1)).login(loginRequest);

    }


}
