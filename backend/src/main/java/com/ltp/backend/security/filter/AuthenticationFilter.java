package com.ltp.backend.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeEditor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ltp.backend.entity.User;
import com.ltp.backend.message.TokenMessage;
import com.ltp.backend.security.SecurityConstants;
import com.ltp.backend.security.manager.CustomAuthenticationManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @Value("${JWT_expiration}")
    private String JWT_Expiration;

    @Value("${JWT_secretKey}")
    private String JWT_secretKey;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // TODO Auto-generated method stub
        try {

            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                    user.getPassword());

            return authenticationManager.authenticate(authentication);
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        // TODO Auto-generated method stub
        List<String> authority = new ArrayList<>();

        for (GrantedAuthority authority2 : authResult.getAuthorities()) {
            authority.add(authority2.toString());
        }
        System.out.println(authority);
        System.out.println(JWT_Expiration);
        String access_token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(
                        new Date(System.currentTimeMillis() +
                                Integer.parseInt(JWT_Expiration)))
                .withClaim("roles", authority)
                .sign(Algorithm.HMAC512(JWT_secretKey));

        String refresh_token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(
                        System.currentTimeMillis() + (2 *
                                Integer.parseInt(JWT_Expiration))))
                .withClaim("roles", authority)
                .sign(Algorithm.HMAC512(JWT_secretKey));

        System.out.println(access_token);
        System.out.println(refresh_token);
        response.setHeader("Authorization", "Bearer " + access_token);
        response.setHeader("RefreshToken", "Bearer " + refresh_token);

        Gson gson = new Gson();
        TokenMessage tokenMessage = new TokenMessage(access_token, refresh_token);
        String responseBody = gson.toJson(tokenMessage);
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(responseBody);
        response.getWriter().flush();

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

}
