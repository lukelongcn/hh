package com.h9.common.common;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by itservice on 2017/11/14.
 */
@Component
public class MailService {
    @Value("${spring.mail.username}")
    private String fromMail;
    @Autowired
    private JavaMailSender mailSender;
    private static final String DEFAULT_EMAIL = "hjsqserver@hey900.com";
    private Logger logger = Logger.getLogger(this.getClass());
    public boolean sendtMail(String subject,String content) {

        sendEmail(subject, content, DEFAULT_EMAIL);

        return true;
    }

    @SuppressWarnings("Duplicates")
    public boolean sendEmail(String subject, String content, String email) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromMail);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        } catch (Exception e) {
            logger.info("------------------------------");
            logger.info("邮件发送失败..........",e);
            logger.info("------------------------------");
            return false;
        }
        logger.info("发送邮件成功");
        return true;
    }

    @SuppressWarnings("Duplicates")
    public boolean sendEmail(String subject, String content, List<String> group) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromMail);
            String[] array = new String[group.size()];
            group.toArray(array);
            message.setTo(array);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        } catch (Exception e) {
            logger.info("------------------------------");
            logger.info("邮件发送失败..........",e);
            logger.info("------------------------------");
            return false;
        }
        logger.info("发送邮件成功");
        return true;
    }


}
