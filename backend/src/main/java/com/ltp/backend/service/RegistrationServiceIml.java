package com.ltp.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.Gson;
import com.ltp.backend.entity.ConfirmationToken;
import com.ltp.backend.entity.Manager;
import com.ltp.backend.entity.RefreshToken;
import com.ltp.backend.entity.User;
import com.ltp.backend.entity.UserRegistrationInformation;
import com.ltp.backend.exception.UnauthorizationException;
import com.ltp.backend.message.ConfirmationMessage;
import com.ltp.backend.message.TokenMessage;
import com.ltp.backend.repository.ConfirmationTokenRepository;
import com.ltp.backend.repository.ManagerRepository;
import com.ltp.backend.repository.UserRepository;

import java.util.Date;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class RegistrationServiceIml implements RegistrationService {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Value("${host.url}")
    private String hostURL;

    @Value("${JWT_secretKey}")
    private String JWT_secretKey;

    @Value("${JWT_expiration}")
    private String JWT_expiration;

    @Override
    public String registerUser(UserRegistrationInformation userRegistrationInformation) {
        // TODO Auto-generated method stub
        String token = userService.signUpUser(userRegistrationInformation);

        String link = hostURL + "/auth/confirmationToken?token=" + token;

        emailService.sendEmail(userRegistrationInformation.getEmail(),
                buildEmail(userRegistrationInformation.getUsername(), link));

        return "Register successfully! Please check your email";

    }

    public String buildEmail(String username, String link) {

        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
                +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
                +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n"
                +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
                +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi "
                + username
                + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
                + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";

    }

    @Override
    public ConfirmationMessage getConfirmationToken(String token) {

        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);

        if (confirmationToken.isPresent()) {

            ConfirmationToken confirmationToken2 = confirmationToken.get();

            if (confirmationToken2.getConfirmedAt() != null) {
                return new ConfirmationMessage(confirmationToken2.getUser().getUsername(),
                        "This token is already activated!");
            }

            if (confirmationToken2.getExpiresAt().isBefore(LocalDateTime.now())) {
                return new ConfirmationMessage(confirmationToken2.getUser().getUsername(),
                        "This token is invalid due to overtime");
            }

            confirmationTokenRepository.updateConfirmedAtWithToken(LocalDateTime.now(), token);
            userRepository.updateChecksWithEmail(confirmationToken2.getUser().getEmail());

            String role = confirmationToken2.getUser().getRoles().stream().filter(x -> x.getRole().equals("admin"))
                    .collect(Collectors.toList()).get(0).getRole();

            Manager manager = new Manager(role, confirmationToken2.getUser());

            User user = userService.getUserWithId(confirmationToken2.getUser().getId());

            user.setManager(manager);
            userRepository.save(user);

            return new ConfirmationMessage(confirmationToken2.getUser().getUsername(),
                    "Thank you! You activated your email successfully!");

        } else {
            return new ConfirmationMessage("", "This token does not exist. Please check again!");
        }
    }

    @Override
    public TokenMessage getRefreshToken(RefreshToken refreshToken) {

        try {

            String token = refreshToken.getRefreshToken();

            String email = JWT.require(Algorithm.HMAC512(JWT_secretKey))
                    .build()
                    .verify(token)
                    .getSubject();

            List<String> roles = JWT.require(Algorithm.HMAC512(JWT_secretKey))
                    .build()
                    .verify(token)
                    .getClaim("roles")
                    .asList(String.class);

            String access_token = JWT.create()
                    .withSubject(email)
                    .withExpiresAt(new Date(System.currentTimeMillis() + Integer.parseInt(JWT_expiration)))
                    .withClaim("roles", roles)
                    .sign(Algorithm.HMAC512(JWT_secretKey));

            String refresh_token = JWT.create()
                    .withSubject(email)
                    .withExpiresAt(new Date(System.currentTimeMillis() + (2 * Integer.parseInt(JWT_expiration))))
                    .withClaim("roles", roles)
                    .sign(Algorithm.HMAC512(JWT_secretKey));

            response.setHeader("Authorization", "Bearer " + access_token);
            response.setHeader("RefreshToken", "Bearer " + refresh_token);

            TokenMessage tokenMessage = new TokenMessage(access_token, refresh_token);

            return tokenMessage;

        } catch (JWTVerificationException ex) {
            throw new UnauthorizationException(
                    "Something wrong with the refresh token! Unauthorized to load new access token and refresh token!");
        }
    }

    @Override
    public void logOut() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
