package com.xpress.xpresspayment.user;

import com.xpress.xpresspayment.data.dtos.requests.RegistrationRequest;
import com.xpress.xpresspayment.data.dtos.responses.RegistrationResponse;
import com.xpress.xpresspayment.exceptions.XpressException;
import com.xpress.xpresspayment.models.AppUser;

import java.util.List;

public interface UserService {
    AppUser findUserByEmail(String email) throws XpressException;

    void saveUser(AppUser user);

    RegistrationResponse register(RegistrationRequest registrationRequest) throws XpressException;

    boolean existByEmail(String email);
    List<AppUser> findAllDisabledUsers();

    void disableUser(AppUser appUser);

    void registerFailedLogin(AppUser appUser);
}



