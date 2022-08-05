package top.imwonder.mcauth.services.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import top.imwonder.mcauth.services.Email;

@Service
public class EmailImpl implements Email {
    @Autowired
    MailProperties mp;
    @Autowired
    JavaMailSenderImpl mailSender;
    @Override
    public String sentEmail(String userEmail) {

        SimpleMailMessage message=new SimpleMailMessage();
        String code = RandomStringUtils.randomAlphanumeric(5);
        message.setFrom(mp.getUsername());
        message.setTo(userEmail);
        message.setSubject("口袋村注册码");
        message.setText("您的验证码是 "+code);
        mailSender.send(message);

        return code;
    }
}
