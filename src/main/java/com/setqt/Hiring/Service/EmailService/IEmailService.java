package com.setqt.Hiring.Service.EmailService;

import jakarta.mail.MessagingException;

public interface IEmailService {
    public void sendHtmlEmail(String sender, String recipient, String subject, String html) throws MessagingException;
}
