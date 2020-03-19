package com.qf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class ShopRegistServiceApplicationTests {

    @Autowired
    private JavaMailSender sender;

	@Test
	void contextLoads() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("测试邮件主题");
        mailMessage.setText("测试邮件内容");
        mailMessage.setFrom("aspio8888@163.com");
        mailMessage.setTo("1193435894@qq.com");
        sender.send(mailMessage);
	}

}
