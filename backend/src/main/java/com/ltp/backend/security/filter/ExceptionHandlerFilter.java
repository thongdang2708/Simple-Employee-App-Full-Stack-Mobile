package com.ltp.backend.security.filter;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ltp.backend.exception.UnauthorizationException;
import com.ltp.backend.exception.UserNotFoundWithEmailException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UserNotFoundWithEmailException ex) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write("The user with this email does not exist!");
            response.getWriter().flush();

        } catch (AccessDeniedException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Cannot access this route!");
            response.getWriter().flush();
        } catch (JWTVerificationException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter()
                    .write("Token is invalid due to out of time or the token is wrong. Please check again!");
            response.getWriter().flush();
        } catch (RuntimeException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Bad Request");
            response.getWriter().flush();
        }
    }
}
