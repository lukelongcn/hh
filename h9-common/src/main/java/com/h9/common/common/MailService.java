package com.h9.common.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created by itservice on 2017/11/14.
 */
@Component
public class MailService {
    @Value("${spring.mail.username}")
    private String fromMail;
    @Autowired
    private JavaMailSender mailSender;

    public boolean sendtMail(String subject,String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        //TODO 替换成邮件组
        message.setTo("756034624@qq.com");
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);

        return true;
    }
}
