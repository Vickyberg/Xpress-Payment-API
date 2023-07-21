package com.xpress.xpresspayment.security.services.auth;

import com.xpress.xpresspayment.data.dtos.requests.LoginRequest;
import com.xpress.xpresspayment.data.dtos.responses.LoginResponse;
import com.xpress.xpresspayment.exceptions.XpressException;

import java.util.Map;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest) throws XpressException;

    String logout(String email);


}
