package com.xpress.xpresspayment.user;

import com.xpress.xpresspayment.data.dtos.requests.PaymentRequest;
import com.xpress.xpresspayment.data.dtos.requests.RegistrationRequest;
import com.xpress.xpresspayment.data.dtos.responses.PaymentResponse;
import com.xpress.xpresspayment.data.dtos.responses.RegistrationResponse;
import com.xpress.xpresspayment.exceptions.XpressException;
import com.xpress.xpresspayment.models.AppUser;
import com.xpress.xpresspayment.models.Validator;
import com.xpress.xpresspayment.models.VerificationToken;
import com.xpress.xpresspayment.models.repositories.UserRepository;
import com.xpress.xpresspayment.user.verification.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;

    private  final ModelMapper mapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;

    private  final VerificationTokenService verificationTokenService;



    @Override
    public AppUser findUserByEmail(String email) throws XpressException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new XpressException("Invalid username or password")
        );
    }
    @Override
    public void saveUser(AppUser user) {userRepository.save(user);

    }

    @Override
    public RegistrationResponse register(RegistrationRequest registrationRequest) throws XpressException {
        validateRegistrationRequest(registrationRequest);
        registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        AppUser appUser = mapper.map(registrationRequest, AppUser.class);
//        VerificationToken verificationToken = verificationTokenService.createRegistrationToken(appUser.getEmail());
        userRepository.save(appUser);
        return mapper.map(appUser, RegistrationResponse.class);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private void validateRegistrationRequest(RegistrationRequest registrationRequest) throws XpressException {

        Validator.ensureValidPhone(registrationRequest.getPhoneNumber());
        Validator.ensureValidEmail(registrationRequest.getEmail(), "email");
        Validator.ensureValidPassword(registrationRequest.getPassword());
        Validator.ensureBothPasswordMatches(registrationRequest.getPassword(), registrationRequest.getConfirmPassword());
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new XpressException("email already exists");
        }

    }
    @Override
    public List<AppUser> findAllDisabledUsers() {
        Page<AppUser> users = userRepository.findByEnabledFalse(Pageable.ofSize(100));
        return users.getContent();
    }

    @Override
    public void disableUser(AppUser appUser) {
        appUser.setEnabled(false);
        appUser.setTimeLocked(LocalDateTime.now());
        saveUser(appUser);
    }

    @Override
    public void registerFailedLogin(AppUser appUser) {
        appUser.setFailedLoginAttempt(appUser.getFailedLoginAttempt() + 1);
        saveUser(appUser);
    }



}
