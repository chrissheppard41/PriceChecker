package com.sparky.Price.SendEmail.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

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

    public void preSend(String content, String subject, List<SendEmail> sendTo) throws MessagingException {
        if(sendTo.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List is Empty, therefore unable to send to anybody");
        }

        String[] sendToPeople = sendTo.stream()
                .map(SendEmail::getEmail)
                .toArray(String[]::new);

        this.send(content, subject, sendToPeople);
    }

    public void preSend(String content, String subject, String[] sendTo) throws MessagingException {
        if(sendTo.length == 0) {
            throw new ArrayIndexOutOfBoundsException("Array is Empty, therefore unable to send to anybody");
        }

        this.send(content, subject, sendTo);
    }

    /**
     * The send method takes consumes the method arguements to format and send an email
     * @param content The body of the email
     * @param subject Used to set the subject of the email
     * @param sendTo send to these emails address
     * @throws MessagingException throws messagingException if format violation
     */
    private void send(String content, String subject, String[] sendTo) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        helper = new MimeMessageHelper(message, true); // true indicates
        // multipart message
        helper.setFrom("cshepoth@gmail.com");
        helper.setSubject(subject);
        helper.setTo(sendTo);
        helper.setText(content, true); // true indicates html
        // continue using helper object for more functionalities like adding attachments, etc.
        javaMailSender.send(message);

    }

}
