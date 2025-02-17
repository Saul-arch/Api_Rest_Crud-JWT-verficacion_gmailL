package com.example.leccion12jwt.service.impl;

import com.example.leccion12jwt.dao.CodigoVerificacionRepository;
import com.example.leccion12jwt.dao.DaoUsuario;
import com.example.leccion12jwt.models.EmailCodDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.example.leccion12jwt.models.CodigoVerificacion;

import java.security.SecureRandom;
import java.time.LocalDateTime;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javamailSender; // java mailSender se usa para enviar correos en srping boot
    @Autowired
    private TemplateEngine templateEngine; // se usa para generar contenido html dinamico en los correos

    @Autowired
    private CodigoVerificacionRepository codigoVerificacionRepository;


    @Override
    public void sendEmail(EmailCodDTO email) throws MessagingException {
        try {
            String CodigoVerificacion = generarCodigo();

            // Guardar el código en la base de datos
            CodigoVerificacion nuevoCodigo = new CodigoVerificacion();
            nuevoCodigo.setEmail(email.getDestinatario());
            nuevoCodigo.setCodigo(CodigoVerificacion);
            nuevoCodigo.setFechaCreacion(LocalDateTime.now());
            nuevoCodigo.setFechaExpiracion(LocalDateTime.now().plusMinutes(10));  // Código válido por 10 minutos
            codigoVerificacionRepository.save(nuevoCodigo);

            // Resto del código para enviar el correo
            MimeMessage message = javamailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF8");

            helper.setTo(email.getDestinatario());
            helper.setSubject(email.getAsunto());

            Context context = new Context();
            context.setVariable("mensaje", "Tu codigo de verificacion es: " + CodigoVerificacion);
            email.setCodigoVerificacion(CodigoVerificacion);
            String contentHTML = templateEngine.process("email", context);

            helper.setText(contentHTML, true);

            javamailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Boolean verifyCodEmail(String codEmail, String correo) {
        // Buscar el código en la base de datos
        CodigoVerificacion codigoVerificacion = codigoVerificacionRepository.findByEmailAndCodigo(correo, codEmail);

        if (codigoVerificacion != null) {
            // Verificar si el código ha expirado
            if (codigoVerificacion.getFechaExpiracion().isAfter(LocalDateTime.now())) {
                System.out.println("Código de verificación correcto");
                codigoVerificacionRepository.delete(codigoVerificacion);
                return true;
            } else {
                System.out.println("El código ha expirado");
            }
        }

        System.out.println("Código de verificación incorrecto");
        return false;
    }


    private String generarCodigo() {
        SecureRandom random = new SecureRandom();
        int codigo = 100000 + random.nextInt(900000); // Genera un número entre 100000 y 999999
        return String.valueOf(codigo);
    }

}
