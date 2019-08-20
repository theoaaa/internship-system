package org.scau.internshipsystem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scau.internshipsystem.system.entity.Internship;
import org.scau.internshipsystem.system.entity.Mail;
import org.scau.internshipsystem.system.entity.Offer;
import org.scau.internshipsystem.system.entity.User;
import org.scau.internshipsystem.system.mapper.UserMapper;
import org.scau.internshipsystem.system.service.MailService;
import org.scau.internshipsystem.system.service.impl.MailServiceImpl;
import org.scau.internshipsystem.system.service.impl.OfferServiceImpl;
import org.scau.internshipsystem.system.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InternshipSystemApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;
    @Resource
    private OfferServiceImpl offerServiceImpl;
    @Resource
    private MailService mailService;
    @Test
    public void contextLoads() {
        Mail mail = Mail.builder()
                .content("邮件内容测试...")
                .subject("邮件主题测试...")
                .to("912856276@qq.com")
                .from("623144099@qq.com")
                .build();
        mailService.sendPlainMessage(mail);
    }

}
