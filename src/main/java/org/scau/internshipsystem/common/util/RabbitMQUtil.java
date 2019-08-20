package org.scau.internshipsystem.common.util;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @program: internship-system
 * @description: rabbitMQ工具类
 * @author: guest
 * @create: 2019-08-19 10:10
 **/
public class RabbitMQUtil {
    public static boolean sendObject(Object o) {
        RabbitTemplate rabbitTemplate = SpringContextUtil.getBean(RabbitTemplate.class);
        rabbitTemplate.convertAndSend("scau.internship", o);
        return true;
    }
}
