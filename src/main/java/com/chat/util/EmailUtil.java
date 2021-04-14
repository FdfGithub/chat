package com.chat.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component("emailUtil")
public class EmailUtil {
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String me;

    public String sendCode(String receive, String subject) {
        String code = randomCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText("验证码为：" + code);
        message.setFrom(me);
        message.setTo(receive);
        mailSender.send(message);
        return code;
    }

    //产生6位的随机验证码
    private String randomCode() {
        //100000-999999
        int code = (int) (Math.random() * 900000 + 100000);
        return String.valueOf(code);
    }
}
