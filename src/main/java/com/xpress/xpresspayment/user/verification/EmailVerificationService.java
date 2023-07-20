package com.xpress.xpresspayment.user.verification;

import com.xpress.xpresspayment.exceptions.XpressException;

public interface EmailVerificationService {
    String verifyEMail(String token) throws XpressException;
    String resendUserRegistrationOTP(String email) throws XpressException;
}
