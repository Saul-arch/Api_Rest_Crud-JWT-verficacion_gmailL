package com.example.leccion12jwt.service.impl;

import com.example.leccion12jwt.models.EmailCodDTO;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(EmailCodDTO email) throws MessagingException;
    Boolean verifyCodEmail(String codEmail, String correo);
}
