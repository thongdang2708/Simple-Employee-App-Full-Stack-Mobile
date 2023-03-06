package com.ltp.backend.service;

import com.ltp.backend.entity.RefreshToken;
import com.ltp.backend.entity.UserRegistrationInformation;
import com.ltp.backend.message.ConfirmationMessage;
import com.ltp.backend.message.TokenMessage;

public interface RegistrationService {
    String registerUser(UserRegistrationInformation userRegistrationInformation);

    ConfirmationMessage getConfirmationToken(String token);

    TokenMessage getRefreshToken(RefreshToken refreshToken);

    void logOut();
}
