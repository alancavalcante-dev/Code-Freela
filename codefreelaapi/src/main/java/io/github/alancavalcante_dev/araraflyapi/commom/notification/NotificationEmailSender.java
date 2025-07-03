package io.github.alancavalcante_dev.araraflyapi.commom.notification;


import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@Slf4j
@Component
public class NotificationEmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void send(@Email String to, String subject, String htmlContent) {
        System.out.println("Thread: " + Thread.currentThread().getName());

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // Segundo parâmetro `true` ativa o modo HTML

            javaMailSender.send(message);
            log.info("E-mail HTML enviado com sucesso para {}", to);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail para {}: {}", to, e.getMessage(), e);
        }
    }
}
