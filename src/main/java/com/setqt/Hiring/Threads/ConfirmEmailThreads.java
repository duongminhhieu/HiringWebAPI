package com.setqt.Hiring.Threads;

import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Model.Employer;
import com.setqt.Hiring.Service.EmailService.EmailService;
import jakarta.mail.MessagingException;
import org.apache.commons.io.FileUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ConfirmEmailThreads implements Runnable{

    private Company com;
    private Employer em;
    private Environment environment;
    private PasswordEncoder passEncoder;
    private EmailService emailService;

//    public final String CONFIRM_STRING = "Xác nhận đăng ký tài khoản doanh nghiệp Jore";
//    public final String CONFIRM_STRING = "X\u00E1c nh\u1EADn \u0111\u0103ng k\u00FD t\u00E0i kho\u1EA3n doanh nghi\u1EC7p Jore";
    public final String CONFIRM_STRING = "Confirm business account registration on Jore";
    public ConfirmEmailThreads(Company com, Employer em, Environment environment, PasswordEncoder passEncoder, EmailService emailService) {
        this.com = com;
        this.em = em;
        this.environment = environment;
        this.passEncoder = passEncoder;
        this.emailService = emailService;
    }

    @Override
    public void run() {
        //send verify email
        String html = null;
        try {
            html = FileUtils.readFileToString(new File("src/main/java/com/setqt/Hiring/Utils/verifyEmailTemplate.html"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        html = html.replace("${user.name}", com.getName());
        html = html.replace("${app_url}", Objects.requireNonNull(environment.getProperty("app_url")));
        html = html.replace("${user.id}", em.getUserId().toString());
        html = html.replace("${hashId}", passEncoder.encode(em.getUserId().toString()));

        try {
            emailService.sendHtmlEmail("jobhiringweb@gmail.com", em.getEmail(), CONFIRM_STRING, html);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
