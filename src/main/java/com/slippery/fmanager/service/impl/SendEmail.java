package com.slippery.fmanager.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {
    private final JavaMailSender mailSender;

    public SendEmail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void Mail(String to,String subject,String body){
        SimpleMailMessage message =new SimpleMailMessage();
        try{
            message.setFrom("noreply@johnmuniu477gmail.com");
            message.setTo(to);
            message.setText(body);
            message.setSubject(subject);
            message.setReplyTo("johnmuniu477@gmail.com");
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
}
