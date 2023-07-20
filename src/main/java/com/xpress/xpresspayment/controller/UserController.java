package com.xpress.xpresspayment.controller;

import com.xpress.xpresspayment.data.dtos.requests.RegistrationRequest;
import com.xpress.xpresspayment.data.dtos.responses.RegistrationResponse;
import com.xpress.xpresspayment.exceptions.XpressException;
import com.xpress.xpresspayment.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/xpress-payment/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) throws  XpressException {
        return new ResponseEntity<>(userService.register(registrationRequest), HttpStatus.CREATED);
    }
}
