package com.ltp.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.ltp.backend.security.exception.AccessException;
import com.ltp.backend.security.exception.CustomAuthenticationEntryPoint;
import com.ltp.backend.security.filter.AuthenticationFilter;
import com.ltp.backend.security.filter.ExceptionHandlerFilter;
import com.ltp.backend.security.filter.JWTAuthorizationFilter;
import com.ltp.backend.security.manager.CustomAuthenticationManager;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

        @Autowired
        private CustomAuthenticationManager authenticationManager;

        @Value("${JWT_expiration}")
        private String JWT_Expiration;

        @Value("${JWT_secretKey}")
        private String JWT_secretKey;

        @Autowired
        private AccessException accessException;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager,
                                JWT_Expiration,
                                JWT_secretKey);
                authenticationFilter.setFilterProcessesUrl("/login");

                http.exceptionHandling().accessDeniedHandler(accessException);

                http
                                .cors()
                                .and()
                                .csrf().disable()
                                .httpBasic()
                                .and()
                                .authorizeHttpRequests(requests -> requests
                                                .requestMatchers("/auth/**").permitAll()
                                                .requestMatchers("/departments/**").permitAll()
                                                .requestMatchers("/employees/**").permitAll()
                                                .anyRequest().authenticated())
                                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                                .addFilter(authenticationFilter)
                                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.logout((logout) -> logout
                                .logoutUrl("/logout")
                                .addLogoutHandler((request, response, auth) -> {
                                        SecurityContextHolder.getContext().setAuthentication(null);
                                        ;
                                }).clearAuthentication(true).permitAll());

                return http.build();
        }
}
