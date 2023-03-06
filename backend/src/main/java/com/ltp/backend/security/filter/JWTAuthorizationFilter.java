package com.ltp.backend.security.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ltp.backend.security.SecurityConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ") || header.split(" ").length == 1) {
            filterChain.doFilter(request, response);
            return;

        }

        String token = header.replace("Bearer ", "");

        String email = JWT.require(Algorithm.HMAC512(SecurityConstants.secretKey))
                .build()
                .verify(token)
                .getSubject();

        Set<GrantedAuthority> authorities = new HashSet<>();

        List<String> roles = JWT.require(Algorithm.HMAC512(SecurityConstants.secretKey))
                .build()
                .verify(token)
                .getClaim("roles")
                .asList(String.class);

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        System.out.println(authorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }
}
