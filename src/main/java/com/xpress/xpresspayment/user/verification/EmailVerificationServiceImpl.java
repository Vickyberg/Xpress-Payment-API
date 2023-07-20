package com.xpress.xpresspayment.user.verification;

import com.xpress.xpresspayment.exceptions.XpressException;
import com.xpress.xpresspayment.models.AppUser;
import com.xpress.xpresspayment.models.TokenType;
import com.xpress.xpresspayment.models.VerificationToken;
import com.xpress.xpresspayment.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailVerificationServiceImpl implements  EmailVerificationService {

    private final VerificationTokenService verificationTokenService;

    private final UserService userService;
    @Override
    public String verifyEMail(String token) throws XpressException{
        VerificationToken verificationToken = verificationTokenService
                .verifyTokenAndFind(token, TokenType.EMAIL_VERIFICATION);
        AppUser user = userService.findUserByEmail(verificationToken.getEmail());
        user.setVerified(true);
        user.setEnabled(true);
        userService.saveUser(user);
        verificationTokenService.deleteToken(verificationToken.getId());
        return "Email verified Successfully";
    }

    @Override
    public String resendUserRegistrationOTP(String email) throws XpressException {
        AppUser user = userService.findUserByEmail(email);
        if (user.isVerified()) throw new XpressException("user is verified, proceed to login");
        verificationTokenService.delete(email, TokenType.EMAIL_VERIFICATION);
        VerificationToken verificationToken = verificationTokenService.generateEMailVerificationToken(email);
        return "email re-sent successfully";
    }
}

