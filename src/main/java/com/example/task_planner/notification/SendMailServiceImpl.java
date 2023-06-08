package com.example.task_planner.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendMailServiceImpl implements SendMailService<String,String>{
    @Value("${spring.mail.username}")
    private String username;
    private final JavaMailSender javaMailSender;

    @Autowired
    public SendMailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void sendMail(String mail,String message) {
        SimpleMailMessage mailMessage=new SimpleMailMessage();


        mailMessage.setFrom(username);

        mailMessage.setTo(mail);

        mailMessage.setSubject("To DO");
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

}