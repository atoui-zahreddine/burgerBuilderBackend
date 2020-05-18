package com.burgerbuilder.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@EnableAsync
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine template;
    private final String PASSWORD_RESET_EMAIL_TEMPLATE="password-reset-template.html";
    private final String PASSWORD_RESET_EMAIL_SUBJECT="Reset your password";

    @Autowired
    public EmailService(JavaMailSender mailSender, SpringTemplateEngine template) {
        this.mailSender = mailSender;
        this.template = template;
    }


    private void sendMail(String to,String subject,String body) throws MessagingException {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(
                message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body,true);
        mailSender.send(message);
    }
    @Async
    public void sendPasswordResetMail(String to, Context context) throws MessagingException {
        String html=template.process(PASSWORD_RESET_EMAIL_TEMPLATE,context);
        sendMail(to,PASSWORD_RESET_EMAIL_SUBJECT,html);
    }

    @Async
    public void sendEmailVerificationMail(String to,Context context) throws MessagingException {
        String html=template.process(PASSWORD_RESET_EMAIL_TEMPLATE,context);
        sendMail(to,PASSWORD_RESET_EMAIL_SUBJECT,html);
    }

}
