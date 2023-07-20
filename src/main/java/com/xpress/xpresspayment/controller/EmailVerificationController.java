package com.xpress.xpresspayment.controller;

import com.xpress.xpresspayment.exceptions.XpressException;
import com.xpress.xpresspayment.user.verification.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/xpress-payment/v1/auth")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/verify-email/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token) throws XpressException {
        return ResponseEntity.ok(emailVerificationService.verifyEMail(token));
    }
}
