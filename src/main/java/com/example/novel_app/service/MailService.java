package com.example.novel_app.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

@Service
public class MailService {
    @NonFinal
    @Value("${spring.mail.username}")
    String fromEmail;
    @NonFinal
    @Value("${spring.mail.password}")
    String password;
    @NonFinal
    @Value("${spring.mail.host}")
    String mailHost;
    @NonFinal
    @Value("${spring.mail.port}")
    String mailPort;
    public void sendmail(String toEmail, String subject, String content) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", mailHost);
            props.put("mail.smtp.port", mailPort);

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(toEmail));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            message.setSentDate(new Date());

            String htmlContent = content;

            message.setContent(htmlContent, "text/html;charset=UTF-8");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}