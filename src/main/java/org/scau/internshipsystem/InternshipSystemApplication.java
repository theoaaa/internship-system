package org.scau.internshipsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("org.scau.internshipsystem.system.mapper")
@SpringBootApplication
@EnableTransactionManagement
@EnableRabbit
public class InternshipSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternshipSystemApplication.class, args);
    }

}
