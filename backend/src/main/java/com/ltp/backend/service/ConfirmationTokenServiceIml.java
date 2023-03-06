package com.ltp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.backend.entity.ConfirmationToken;
import com.ltp.backend.repository.ConfirmationTokenRepository;

@Service
public class ConfirmationTokenServiceIml implements ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        // TODO Auto-generated method stub
        confirmationTokenRepository.save(confirmationToken);
    }
}
