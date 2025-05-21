package io.github.alancavalcante_dev.codefreelaapi.domain.notification;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationEmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String to, String subject, String text) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(text);

            javaMailSender.send(mailMessage);
            log.info("E-mail enviado com sucesso para {}", to);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail para {}: {}", to, e.getMessage(), e);
        }
    }
}
