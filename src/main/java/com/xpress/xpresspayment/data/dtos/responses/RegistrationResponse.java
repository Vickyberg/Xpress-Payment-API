package com.xpress.xpresspayment.data.dtos.responses;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class RegistrationResponse {
    private String email;
    private String message = "You have  successfully registered";
}
