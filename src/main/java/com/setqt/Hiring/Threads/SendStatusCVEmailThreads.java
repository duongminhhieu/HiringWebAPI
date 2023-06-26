package com.setqt.Hiring.Threads;

import com.setqt.Hiring.Service.EmailService.EmailService;
import jakarta.mail.MessagingException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SendStatusCVEmailThreads implements Runnable{
    private Environment environment;

    private EmailService emailService;

    private String email;
    private String name;
    private String jobPostingName;
    private String company;

    public SendStatusCVEmailThreads(String email, String name, String jobPostingName, String company, EmailService emailService) {
        this.email = email;
        this.name = name;
        this.jobPostingName = jobPostingName;
        this.company = company;
        this.emailService = emailService;
    }

    @Override
    public void run() {
//send reset email
        String html = null;
        try {
            html = FileUtils.readFileToString(new File("src/main/java/com/setqt/Hiring/Utils/passEmailForm.html"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        html = html.replace("${user.name}", name);
        html = html.replace("${company}", company);
        html = html.replace("${jobPosting}", jobPostingName);


        try {
            emailService.sendHtmlEmail("jobhiringweb@gmail.com", email, "CONGRATULATIONS !!!!", html);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
