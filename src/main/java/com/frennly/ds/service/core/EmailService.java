package com.frennly.ds.service.core;

import com.frennly.ds.exception.EmailException;

public interface EmailService {
    public void sendMail(String toEmail, String subject, String message) throws EmailException;
}
