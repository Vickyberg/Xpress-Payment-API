package com.xpress.xpresspayment.user.verification;

import com.xpress.xpresspayment.exceptions.XpressException;
import com.xpress.xpresspayment.models.TokenType;
import com.xpress.xpresspayment.models.VerificationToken;
import com.xpress.xpresspayment.models.repositories.VerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class VerificationTokenServiceImpl implements  VerificationTokenService{
    private final VerificationRepository verificationTokenRepository;


    @Override
    public void deleteToken(Long id) {
        verificationTokenRepository.deleteById(id);
    }

    @Override
    public VerificationToken createLoginOtp(String email) {
        return verificationTokenRepository.save(
                new VerificationToken(
                        getSixDigitToken(),
                        LocalDateTime.now().plusMinutes(5),
                        email,
                        TokenType.LOGIN_OTP
                )
        );
    }

    @Override
    @Transactional
    public void delete(String email, TokenType tokenType) {
        verificationTokenRepository.deleteByEmailAndTokenType(email, tokenType);
    }



    @Override
    public VerificationToken verifyTokenAndFind(String token, TokenType tokenType) throws  XpressException {
        VerificationToken verificationToken = verificationTokenRepository.findByTokenAndTokenType(token, tokenType).orElseThrow(
                () -> new XpressException("invalid or expired")
        );
        if (verificationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new XpressException("This token has expired!!");
        }
        return verificationToken;
    }

    public String getSixDigitToken() {
        SecureRandom secureRandom = new SecureRandom();
        int number = secureRandom.nextInt(999999);
        String res =  String.format("%06d", number);
        System.out.println(res);
        if(verificationTokenRepository.existsByToken(res)){
            return getSixDigitToken();
        }
        System.out.println("leaving Xpress");
        return res;
    }

}
