package io.github.alancavalcante_dev.codefreelaapi.domain.notification;


import org.springframework.mail.javamail.JavaMailSender;

public class NotificationEmailSender {

    private JavaMailSender javaMailSender;

    public void teste() {
        javaMailSender.send();
    }

}
