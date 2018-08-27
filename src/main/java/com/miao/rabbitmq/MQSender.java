package com.miao.rabbitmq;

import com.miao.miaosha.service.RedisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {
    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(Object message) {
        String msg = RedisService.beanToString(message);
        System.out.println("发送消息："+msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE_1,msg);

    }

    public void sendMiaoshaMsg(MiaoshaMsg miaoshaMsg) {
        String msg = RedisService.beanToString(miaoshaMsg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE_1,msg);

    }
}
