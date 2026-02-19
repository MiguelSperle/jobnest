package com.miguel.jobnest.infrastructure.services;

import com.miguel.jobnest.infrastructure.abstractions.services.EmailService;
import com.miguel.jobnest.infrastructure.exceptions.EmailSendFailedException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpEmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    private static final Logger log = LoggerFactory.getLogger(SmtpEmailServiceImpl.class);

    @Override
    public void sendEmail(final String to, final String text, final String subject) {
        try {
            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);

            mimeMessageHelper.setFrom(this.from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(text, false);
            mimeMessageHelper.setSubject(subject);

            this.javaMailSender.send(mimeMessage);
        } catch (Exception ex) {
            log.error("Failed to send email to: {}", to, ex);
            throw EmailSendFailedException.with("Failed to send email", ex);
        }
    }
}
