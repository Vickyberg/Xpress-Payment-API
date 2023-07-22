package com.xpress.xpresspayment.user.verification;

import com.xpress.xpresspayment.models.repositories.VerificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = VerificationTokenServiceImpl.class)
class VerificationTokenServiceImplTest {
    @MockBean
    private VerificationRepository verificationTokenRepository;

    @Autowired
    private VerificationTokenServiceImpl verificationTokenService;

    @Test
    void getSixDigitToken() {
        when(verificationTokenRepository.existsByToken(any())).thenReturn(false);
        String a = verificationTokenService.getSixDigitToken();
    }

}