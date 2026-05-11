package com.his.correspondence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendMail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom("spring.vinay@gmail.com"); // verified sender
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            return true; // success
        } catch (Exception e) {
            e.printStackTrace();
            return false; // fail
        }
    }



}
