package com.xpress.xpresspayment.data.dtos.responses;


import com.xpress.xpresspayment.models.AppUser;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {
    private String jwtToken;
    private Long userId;
    private final String message = "login Successful";
    private String firstName;
    private String lastName;
    private  String email;
    public static LoginResponse of(String jwtToken, Long userId, AppUser appUser) {
        return new LoginResponse(jwtToken, userId, appUser.getFirstName(), appUser.getLastName(), appUser.getEmail());
    }
}
