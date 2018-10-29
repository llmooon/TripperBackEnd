package org.soma.tripper.user.service;

import org.soma.tripper.user.dto.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void sendMail(Email email) throws MessagingException {
        System.out.println(email.toString());
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        //helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));

        Context context = new Context();
        context.setVariables(email.getModel());
        System.out.println(email.getModel().toString());
        String html = templateEngine.process("emailTemplate", context);

        helper.setSubject(email.getSubject());
        helper.setTo(email.getRecipient());
        helper.setText(html,true);
        helper.setFrom(email.getSender());
        javaMailSender.send(message);
    }
}
