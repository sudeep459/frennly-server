package com.frennly.ds.service.impl;


import com.frennly.ds.exception.EmailException;
import com.frennly.ds.service.core.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(String toEmail, String subject, String message) throws EmailException {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(toEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailMessage.setFrom("team@friennly.in");
            javaMailSender.send(mailMessage);
            log.info("EMailService sendMail - " +  mailMessage);
        } catch (Exception e){
            throw new EmailException(e.getMessage());
        }
    }
}
