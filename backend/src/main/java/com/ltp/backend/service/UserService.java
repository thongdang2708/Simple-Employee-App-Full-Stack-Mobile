package com.ltp.backend.service;

import com.ltp.backend.entity.User;
import com.ltp.backend.entity.UserRegistrationInformation;
import com.ltp.backend.message.UsernameResponse;

public interface UserService {

    String signUpUser(UserRegistrationInformation userRegistrationInformation);

    User getUserWithId(Long id);

    User getUserWithEmail(String email);

    User getLoggedInUserWithEmail(String email);

    User updateUsername(UsernameResponse usernameResponse, Long id);

}
