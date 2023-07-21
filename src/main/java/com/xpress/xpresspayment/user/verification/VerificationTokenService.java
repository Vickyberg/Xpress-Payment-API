package com.xpress.xpresspayment.user.verification;

import com.xpress.xpresspayment.exceptions.XpressException;
import com.xpress.xpresspayment.models.TokenType;
import com.xpress.xpresspayment.models.VerificationToken;

public interface VerificationTokenService {




    void deleteToken(Long id);

    VerificationToken createLoginOtp(String email);

    VerificationToken verifyTokenAndFind(String token, TokenType emailVerification) throws XpressException;

    void delete(String email, TokenType loginOtp);
}
