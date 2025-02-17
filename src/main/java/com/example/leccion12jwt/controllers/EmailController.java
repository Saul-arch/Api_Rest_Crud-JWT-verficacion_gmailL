package com.example.leccion12jwt.controllers;

import com.example.leccion12jwt.models.EmailCodDTO;
import com.example.leccion12jwt.service.impl.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Random;

@RestController
@RequestMapping(value = "/email")
public class EmailController {
    @Autowired
    EmailService emailService;

    @RequestMapping(value = "/sendemail", method = RequestMethod.POST)
    public void EnviarCodigo(@RequestBody EmailCodDTO email) throws MessagingException {

        email.setAsunto("Registro usuario");
        email.setDestinatario(email.getDestinatario());

        emailService.sendEmail(email);
    }

    @RequestMapping(value = "/verifyCod", method = RequestMethod.POST)
    public Boolean VerifyCodigo(@RequestBody EmailCodDTO email) throws MessagingException {

        return emailService.verifyCodEmail(email.getCodigoVerificacion(), email.getDestinatario());
    }

}
