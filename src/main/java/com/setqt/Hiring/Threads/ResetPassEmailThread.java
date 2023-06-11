package com.setqt.Hiring.Threads;

import com.setqt.Hiring.Service.EmailService.EmailService;
import jakarta.mail.MessagingException;
import org.apache.commons.io.FileUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ResetPassEmailThread implements Runnable{

    private String email;
    private String role;
    private Environment environment;
    private PasswordEncoder passEncoder;
    private EmailService emailService;

    public ResetPassEmailThread(String email, String role, Environment environment, PasswordEncoder passEncoder, EmailService emailService) {
        this.email = email;
        this.role = role;
        this.environment = environment;
        this.passEncoder = passEncoder;
        this.emailService = emailService;
    }

    @Override
    public void run() {
        //send reset email
        String html = null;
        try {
            html = FileUtils.readFileToString(new File("src/main/java/com/setqt/Hiring/Utils/resetPasswordMail.html"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        html = html.replace("{$APP_URL}", Objects.requireNonNull(environment.getProperty("app_url")));
        html = html.replace("${user.email}", email);
        html = html.replace("${hashEmail}", passEncoder.encode(email));
        html = html.replace("${role}", role);


        try {
            emailService.sendHtmlEmail("jobhiringweb@gmail.com", email, "RESET PASSWORD", html);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
