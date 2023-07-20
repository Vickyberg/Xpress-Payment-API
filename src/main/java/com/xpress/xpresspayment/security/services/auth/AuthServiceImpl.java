package com.xpress.xpresspayment.security.services.auth;

import com.xpress.xpresspayment.data.dtos.requests.LoginRequest;
import com.xpress.xpresspayment.data.dtos.responses.LoginResponse;

import com.xpress.xpresspayment.exceptions.XpressException;
import com.xpress.xpresspayment.models.AppUser;
import com.xpress.xpresspayment.models.TokenType;
import com.xpress.xpresspayment.models.VerificationToken;
import com.xpress.xpresspayment.security.config.SecureUser;
import com.xpress.xpresspayment.security.services.jwt.JwtService;
import com.xpress.xpresspayment.user.UserService;
import com.xpress.xpresspayment.user.verification.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements  AuthService{

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final JwtService jwtService;
    @Value("${app.loginAttempts}")
    private int LOGIN_ATTEMPTS;
    @Value("${app.lockedDuration}")
    private int ACCOUNT_LOCK_DURATION;

    public LoginResponse login(LoginRequest request) throws XpressException {
        AppUser appUser = userService.findUserByEmail(request.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            if (appUser.getFailedLoginAttempt() == LOGIN_ATTEMPTS - 1) {
                userService.disableUser(appUser);
            } else {
                userService.registerFailedLogin(appUser);
            }
            if (!appUser.isEnabled()) sendAccountLockedErrorMessage(appUser);
            throw new XpressException("Invalid username or password");
        } catch (DisabledException ex) {
            if (appUser.getFailedLoginAttempt() == LOGIN_ATTEMPTS - 1) sendAccountLockedErrorMessage(appUser);
            throw new XpressException("please verify your email");
        }
        String jwtToken = performSuccessLogin(appUser);
        return LoginResponse.of(jwtToken, appUser.getId(), appUser);
    }
    private void sendAccountLockedErrorMessage(AppUser appUser) throws XpressException {
        long minutes = ChronoUnit.MINUTES.between(appUser.getTimeLocked(), LocalDateTime.now());
        throw new XpressException(String.format("Too many login attempts. account locked. try again after %d minutes", ACCOUNT_LOCK_DURATION - minutes));
    }

    private String performSuccessLogin(AppUser appUser) {
        appUser.setFailedLoginAttempt(0);
        userService.saveUser(appUser);
        verificationTokenService.delete(appUser.getEmail(), TokenType.LOGIN_OTP);
        SecureUser user = new SecureUser(appUser);
        String jwtToken = jwtService.generateToken(user);
        return jwtToken;
    }
    @Override
    public String logout(String email) {
        return "Logout Successful";
    }


    @Override
    public Map<String, String> verifyLogin(String otp) throws XpressException {
        VerificationToken verificationToken = verificationTokenService.verifyTokenAndFind(otp, TokenType.LOGIN_OTP);
        verificationTokenService.deleteToken(verificationToken.getId());
        AppUser appUser = userService.findUserByEmail(verificationToken.getEmail());
        return Map.of(
                "message", "login verified"
        );
    }

    @Scheduled(cron = "0 */1 * ? * *")
    public void enableAccount() {
        List<AppUser> disabledUsers = userService.findAllDisabledUsers();
        if (disabledUsers.isEmpty()) return;
        disabledUsers.forEach(user -> {
            if (user.isVerified() && ChronoUnit.MINUTES.between(user.getTimeLocked(), LocalDateTime.now()) >= ACCOUNT_LOCK_DURATION) {
                user.setEnabled(true);
                user.setFailedLoginAttempt(0);
                user.setTimeLocked(null);
                userService.saveUser(user);
            }
        });
    }


}
