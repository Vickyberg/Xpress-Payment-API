package com.xpress.xpresspayment.controller;

import com.xpress.xpresspayment.data.dtos.requests.LoginRequest;
import com.xpress.xpresspayment.data.dtos.responses.LoginResponse;
import com.xpress.xpresspayment.exceptions.XpressException;
import com.xpress.xpresspayment.security.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("api/xpress-payment/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws XpressException{
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/verify-login/{otp}")
    public ResponseEntity<Map<String, String>> verifyLogin(@PathVariable String otp) throws XpressException {
        return ResponseEntity.ok(authService.verifyLogin(otp));
    }

    @PostMapping("/logout/{email}")
    public ResponseEntity<String> logout(@PathVariable String email)  {
        return ResponseEntity.ok(authService.logout(email));
    }
}
