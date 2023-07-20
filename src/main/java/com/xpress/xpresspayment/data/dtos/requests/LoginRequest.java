package com.xpress.xpresspayment.data.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class LoginRequest {
    private String email;
    private String password;
}
