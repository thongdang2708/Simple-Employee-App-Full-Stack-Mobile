package com.ltp.backend.security.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ltp.backend.entity.User;
import com.ltp.backend.service.UserService;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO Auto-generated method stub
        User user = userService.getUserWithEmail(authentication.getName());

        if (!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            throw new BadCredentialsException("Password is incorrect! Please check again!");
        }

        if (user.isChecks() == false) {
            throw new BadCredentialsException("This user is not yet validated by email!");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword(),
                user.getAuthorities());
    }
}
