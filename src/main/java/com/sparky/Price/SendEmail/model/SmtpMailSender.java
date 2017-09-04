package com.sparky.Price.SendEmail.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * SmtpMailSender
 *
 * The main class that handles sending emails
 *
 * @author chris.sheppard
 * Created by chris.sheppard on 24/03/2016.
 */
@Service
public class SmtpMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * The send method takes consumes the method arguements to format and send an email
     * @param content The body of the email
     * @param subject Used to set the subject of the email
     * @param sendTo send to these emails address
     * @throws MessagingException throws messagingException if format violation
     */
    public void send(String content, String subject, String[] sendTo) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;


        helper = new MimeMessageHelper(message, true); // true indicates
        // multipart message
        helper.setFrom("from@test.com");
        helper.setSubject(subject);
        helper.setTo(sendTo);
        helper.setText(content, true); // true indicates html
        // continue using helper object for more functionalities like adding attachments, etc.
        javaMailSender.send(message);
    }

}
