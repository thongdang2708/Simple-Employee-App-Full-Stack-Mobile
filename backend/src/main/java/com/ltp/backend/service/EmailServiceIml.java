package com.ltp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ltp.backend.exception.SendMailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceIml implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceIml.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String hostMail;

    @Override
    public void sendEmail(String toMail, String content) {
        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(content, true);
            helper.setTo(toMail);
            helper.setFrom(hostMail);
            helper.setSubject("Email Verification!");

            mailSender.send(message);

        } catch (MessagingException ex) {
            logger.error("Fail to send email!");
            throw new SendMailException("Fail to send mail!");
        }
    }
}
