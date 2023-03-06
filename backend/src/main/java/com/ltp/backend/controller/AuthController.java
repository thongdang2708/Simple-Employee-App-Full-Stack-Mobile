package com.ltp.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.backend.entity.Email;
import com.ltp.backend.entity.RefreshToken;
import com.ltp.backend.entity.User;
import com.ltp.backend.entity.UserRegistrationInformation;
import com.ltp.backend.message.CommonSuccessfulMessage;
import com.ltp.backend.message.ConfirmationMessage;
import com.ltp.backend.message.TokenMessage;
import com.ltp.backend.message.UserInformationResponse;
import com.ltp.backend.message.UsernameResponse;
import com.ltp.backend.service.RegistrationService;
import com.ltp.backend.service.UserService;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<CommonSuccessfulMessage> registerUser(
            @Valid @RequestBody UserRegistrationInformation userRegistrationInformation) {

        String message = registrationService.registerUser(userRegistrationInformation);

        CommonSuccessfulMessage successfulMessage = new CommonSuccessfulMessage(message);

        return new ResponseEntity<>(successfulMessage, HttpStatus.CREATED);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<HttpStatus> logOutFunction() {
        registrationService.logOut();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/register/test")
    public ResponseEntity<HttpStatus> registerUserTest(
            @Valid @RequestBody UserRegistrationInformation userRegistrationInformation) {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<UserInformationResponse> getUserInformation(
            @RequestParam(value = "email", required = false) String email) {

        User user = userService.getLoggedInUserWithEmail(email);

        UserInformationResponse newUser = new UserInformationResponse(user.getEmail(), user.getUsername(), user.getId(),
                user.getManager().getId());

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("/confirmationToken")
    public String getConfirmationToken(
            @RequestParam(value = "token", required = false) String token) {

        ConfirmationMessage message = registrationService.getConfirmationToken(token);

        return buildConfirmationPage(message);
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<UsernameResponse> updateUser(@Valid @RequestBody UsernameResponse usernameResponse,
            @PathVariable Long id) {

        User user = userService.updateUsername(usernameResponse, id);

        UsernameResponse newUser = new UsernameResponse(user.getUsername());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<TokenMessage> getRefreshToken(@Valid @RequestBody RefreshToken refreshToken) {

        return new ResponseEntity<>(registrationService.getRefreshToken(refreshToken), HttpStatus.OK);
    }

    public String buildConfirmationPage(ConfirmationMessage message) {
        String pageContent = "";
        if (message.getUsername().equals("")) {
            pageContent = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n"
                    +
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
                    "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Big Warning: Failed to confirm this token!"
                    + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">"
                    + message.getContent()
                    + "</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>"
                    +
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

        } else if (!message.getUsername().equals("")) {
            pageContent = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n"
                    +
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
                    + message.getUsername()
                    + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">"
                    + message.getContent()
                    + "</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>"
                    +
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

        return pageContent;
    }
}
