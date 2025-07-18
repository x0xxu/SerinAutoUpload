package com.serinautoupload.utils;


import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class MailUtils {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.to}")
    private String[] listTo;

    @Value("${spring.mail.cc}")
    private String[] listCc;

    public void sendSimpleMail(String msg) throws MessagingException {
        //简单邮件
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setFrom("BU3.SERVER@luxshare-ict.com");
        messageHelper.setSubject("OEEKit数据上传");
        messageHelper.setTo(listTo);
        messageHelper.setCc(listCc);
        messageHelper.setSentDate(new Date());
        messageHelper.setText(msg);
        mailSender.send(message);
    }
}
