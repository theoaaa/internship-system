package org.scau.internshipsystem.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.scau.internshipsystem.system.entity.Mail;
import org.scau.internshipsystem.system.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @program: internship-system
 * @description: 邮件服务
 * @author: guest
 * @create: 2019-08-17 13:05
 **/
@Slf4j
@Service
public class MailServiceImpl implements MailService {
    @Resource
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendPlainMessage(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mail.getContent());
            mailSender.send(mimeMessage);
            log.info("邮件发送成功...");
        } catch (MessagingException e) {
            log.error("邮件发送异常...");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
